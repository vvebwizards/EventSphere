const { initializeAdminClient } = require('../config/keycloak-admin');
const User = require('../models/User');
const axios = require('axios');
require('dotenv').config();
const signUp = async (req, res) => {
  try {
    const { email, password, firstName,lastName, role } = req.body;

    if (!email || !password || !firstName ||!lastName) {
      return res.status(400).json({
        success: false,
        message: 'Veuillez fournir email, mot de passe, nom complet.',
      });
    }

    const keycloakAdminClient = await initializeAdminClient();

    const existing = await keycloakAdminClient.users.find({ email });
    if (existing.length > 0) {
      return res.status(409).json({
        success: false,
        message: 'Cet utilisateur existe déjà dans Keycloak',
      });
    }

    const keycloakUser = {
      username: email,
      email,
      firstName: firstName ,
      lastName: lastName,
      enabled: true,
      emailVerified: false,
      credentials: [{
        type: 'password',
        value: password,
        temporary: false
      }]
    };

    const created = await keycloakAdminClient.users.create(keycloakUser);
    const realmRoles = await keycloakAdminClient.roles.find();
    const userRole = realmRoles.find(r => r.name === role);
    if (userRole) {
      await keycloakAdminClient.users.addRealmRoleMappings({
        id: created.id,
        roles: [{ id: userRole.id, name: userRole.name }]
      });
    }
    const newUser = new User({
      email,
      username: email,
      firstName: firstName ,
      lastName: lastName ,
      role,
      joined_at: new Date()
    });
    await newUser.save();

    return res.status(201).json({
      success: true,
      message: 'Utilisateur enregistré avec succès dans Keycloak et MongoDB.'
    });
  } catch (error) {
    console.error('SignUp error:', error.response?.data || error.message);
    return res.status(500).json({
      success: false,
      message: "Erreur lors de l'enregistrement",
      error: error.message,
    });
  }
};

const signIn = async (req, res) => {
  try {
    const { email, password } = req.body;

    const params = new URLSearchParams();
    params.append('client_id', process.env.KEYCLOAK_CLIENT); 
    params.append('client_secret', process.env.KEYCLOAK_CLIENT_SECRET); 
    params.append('grant_type', 'password');
    params.append('username', email);
    params.append('password', password);

    const tokenResponse = await axios.post(
      `${process.env.KEYCLOAK_URL}/realms/${process.env.KEYCLOAK_REALM_NAME}/protocol/openid-connect/token`,
      params,
      {
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded',
        },
      }
    );

    const { access_token, refresh_token, id_token } = tokenResponse.data;

    return res.json({
      success: true,
      accessToken: access_token,
      refreshToken: refresh_token,
      idToken: id_token,
    });

  } catch (error) {
    console.error('SignIn error:', error.response?.data || error.message);
    return res.status(500).json({
      success: false,
      message: 'Authentication error',
      error: error.response?.data?.error_description || error.message,
    });
  }
};


module.exports = { signUp ,signIn};