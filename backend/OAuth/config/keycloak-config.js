require('dotenv').config();
const session = require('express-session');
const Keycloak = require('keycloak-connect');
const {
  KEYCLOAK_CLIENT_SECRET,
   KEYCLOAK_URL
  } = process.env;
const memoryStore = new session.MemoryStore();

const keycloak = new Keycloak({ store: memoryStore }, {
  "realm": "eventsphere-realm",
  "auth-server-url": KEYCLOAK_URL,
  "ssl-required": "external",
  "resource": "user-management-nodejs",
  "credentials": {
    "secret": KEYCLOAK_CLIENT_SECRET
  },
});

module.exports = { keycloak, memoryStore };
