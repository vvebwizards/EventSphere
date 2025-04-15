const express = require('express');
const router = express.Router();
const userController = require('../controllers/userController');
const verifyToken = require('../middelwares/verifyToken');



router.get("/getMe",userController.getCurrentUser); 
router.post("/logout", userController.logout); 


module.exports = router;
