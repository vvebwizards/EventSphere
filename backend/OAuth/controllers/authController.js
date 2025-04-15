const { initializeAdminClient } = require('../config/keycloak-admin');
const User = require('../models/User');
require('dotenv').config();
const signUp = async (req, res) => {
  try {
    const { email, password, fullName, role } = req.body;

    if (!email || !password || !fullName) {
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
    const [firstName, ...lastNameParts] = fullName.split(' ');
    const lastName = lastNameParts.join(' ');

    const keycloakUser = {
      username: email,
      email,
      firstName: firstName || fullName,
      lastName: lastName || '',
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
      firstName: firstName || fullName,
      lastName: lastName || '',
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
    const keycloakAdminClient = await initializeAdminClient();
    const token = await keycloakAdminClient.auth({
      username: email,
      password: password,
      client_id: 'user-management-nodejs', 
      grant_type: 'password',
    });

    if (!token || !token.access_token) {
      return res.status(403).json({
        success: false,
        message: 'Authentication failed. Invalid email or password.',
      });
    }

    const userInfo = token.content;
    return res.json({
      success: true,
      user: {
        id: userInfo.sub, 
        email: userInfo.email,
        fullName: userInfo.preferred_username, 
        roles: userInfo.realm_access?.roles || [],
      },
      accessToken: token.access_token, 
      refreshToken: token.refresh_token, 
    });
  } catch (error) {
    console.error('SignIn error:', error.response?.data || error.message);
    return res.status(500).json({
      success: false,
      message: 'Authentication error',
      error: error.message,
    });
  }
};

module.exports = { signUp ,signIn};