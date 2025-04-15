const express = require('express');
const { keycloak } = require('../config/keycloak-config');
const User=require('../models/User');


const  getCurrentUser = [
  keycloak.protect(), 
  (req, res) => {
    const userInfo = req.kauth.grant.access_token.content;

    res.json({
      success: true,
      user: {
        id: userInfo.sub,                          
        username: userInfo.preferred_username,     
        email: userInfo.email,                     
        roles: userInfo.realm_access?.roles || []  
      }
    });
  }
];

const logout = [
  keycloak.protect(),  
  async (req, res) => {
    try {
      await keycloak.grantManager.revokeGrant(req.kauth.grant);
      req.session.destroy((err) => {
        if (err) {
          return res.status(500).json({
            success: false,
            message: 'Error destroying session',
            error: err.message,
          });
        }
        res.json({
          success: true,
          message: 'Logged out successfully',
        });
      });
    } catch (error) {
      res.status(500).json({
        success: false,
        message: 'Server error',
        error: error.message,
      });
    }
  }
];
  

module.exports = {
  getCurrentUser,
    logout

};
