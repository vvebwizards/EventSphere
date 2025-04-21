const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const UserSchema = new Schema({
  email: { type: String, unique: true, required: true },
  username: { type: String, required: true }, 
  firstName: { type: String, required: true }, 
  lastName: { type: String }, 
  role: { 
    type: String, 
    required: true, 
    enum: ['resource-owner', 'user', 'event-creator', 'partner'] 
  },

  joined_at: { type: Date, default: Date.now },
  updated_at: { type: Date }
});

UserSchema.pre('save', function (next) {
  this.updated_at = new Date();
  next();
});

module.exports = mongoose.model('User', UserSchema);