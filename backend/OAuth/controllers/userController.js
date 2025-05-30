const express = require('express');
const { keycloak } = require('../config/keycloak-config');
const User=require('../models/User');
const axios = require('axios');
require('dotenv').config();

const  getCurrentUser = [
  keycloak.protect(), 
  (req, res) => {
    const userInfo = req.kauth.grant.access_token.content;
    const roles = userInfo.realm_access?.roles || [];
    const appRoles = ['resource-owner', 'user', 'event-creator', 'partner'];
    const matchedRole = roles.find(role => appRoles.includes(role));
    res.json({
  id: userInfo.sub,
  email: userInfo.email,
  username: userInfo.preferred_username,
  firstName: userInfo.given_name || null,
  lastName: userInfo.family_name || null,
  role: matchedRole || null,
  joined_at: null,
  updated_at: null
});
  }
];

const logout = async (req, res) => {
  try {
    const { refreshToken } = req.body;

    if (!refreshToken) {
      return res.status(400).json({
        success: false,
        message: 'Refresh token is required for logout.',
      });
    }

    const params = new URLSearchParams();
    params.append('client_id', process.env.KEYCLOAK_CLIENT);
    params.append('client_secret', process.env.KEYCLOAK_CLIENT_SECRET); 
    params.append('refresh_token', refreshToken);

    await axios.post(
      `${process.env.KEYCLOAK_URL}/realms/${process.env.KEYCLOAK_REALM_NAME}/protocol/openid-connect/logout`,
      params,
      {
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded',
        },
      }
    );

    res.json({
      success: true,
      message: 'Logged out successfully',
    });

  } catch (error) {
    console.error('Logout error:', error.response?.data || error.message);
    res.status(500).json({
      success: false,
      message: 'Logout failed',
      error: error.response?.data?.error_description || error.message,
    });
  }
};

  

module.exports = {
  getCurrentUser,
    logout

};
