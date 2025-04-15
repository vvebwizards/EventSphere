require('dotenv').config();

let kcAdminClient;

async function initializeAdminClient() {
  if (!kcAdminClient) {
    const { default: KcAdminClient } = await import('@keycloak/keycloak-admin-client');

    kcAdminClient = new KcAdminClient({
      baseUrl: process.env.KEYCLOAK_URL
    });

    await kcAdminClient.auth({
      username: process.env.KEYCLOAK_ADMIN_USERNAME,
      password: process.env.KEYCLOAK_ADMIN_PASSWORD,
      grantType: 'password',
      clientId: 'admin-cli',
    });

    kcAdminClient.setConfig({
      realmName: process.env.KEYCLOAK_REALM_NAME || 'eventsphere-realm',
    });
  }

  return kcAdminClient;
}



module.exports = {
  initializeAdminClient
};
