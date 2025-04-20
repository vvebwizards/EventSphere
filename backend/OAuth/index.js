require('dotenv').config();
const express = require('express');
const cors = require('cors');
const session = require('express-session');
const mongoose = require('mongoose');
const cookieParser = require('cookie-parser');
const authRoutes = require('./routes/authRoutes');
const userRoutes = require('./routes/userRoutes');
const { keycloak, memoryStore } = require('./config/keycloak-config');
const { Eureka } = require('eureka-js-client');

const app = express();
app.use(express.json());

mongoose.connect(
    process.env.NODE_ENV === 'production'
        ? process.env.PROD_DATABASE
        : process.env.DEV_DATABASE,
    {
        useNewUrlParser: true,
        useUnifiedTopology: true,
    }
)
.then(() => {
    console.log('Connected to the database');
})
.catch((err) => {
    console.error('Database connection error:', err);
});


app.use(session({
    secret: "secret",
    resave: false,
    saveUninitialized: true,
    cookie: {
        secure: process.env.NODE_ENV === 'production',
        maxAge: 7 * 24 * 60 * 60 * 1000,
    },
    store: memoryStore
}));

app.use(keycloak.middleware());
app.use(cors());
app.use(cookieParser());
app.use('/user', userRoutes);
app.use('/', authRoutes);

const PORT = process.env.PORT || 3000;
app.listen(PORT, (err) => {
    if (err) {
        console.log(err);
    } else {
        console.log(`Listening on PORT ${PORT}`);

        // Eureka client configuration and registration
        const eureka = new Eureka({
            instance: {
                app: 'user-service', 
                hostName: 'localhost', // or container name if using Docker
                ipAddr: '127.0.0.1',
                port: {
                    '$': PORT,
                    '@enabled': true
                },
                vipAddress: 'user-service',
                dataCenterInfo: {
                    '@class': 'com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo',
                    name: 'MyOwn'
                }
            },
            eureka: {
                host: 'localhost',
                port: 9000,
                servicePath: '/eureka/apps/'
            }
        });

        eureka.start((error) => {
            console.log(error || '✔️ Node.js service registered with Eureka');
        });
    }
});
