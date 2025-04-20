const express = require('express');
const router = express.Router();
const authController = require('../controllers/authController');


router.post("/register", authController.signUp); 
router.post("/signIn", authController.signIn); 
router.post("/reset-password", authController.resetPassword);
router.post("/update-password", authController.updatePassword);


module.exports = router;
