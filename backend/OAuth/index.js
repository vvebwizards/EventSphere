require('dotenv').config();
const express = require('express');
const session = require('express-session');
const mongoose = require('mongoose');
const cookieParser = require('cookie-parser');
const authRoutes = require('./routes/authRoutes');
const userRoutes = require('./routes/userRoutes');
const { keycloak, memoryStore } = require('./config/keycloak-config');
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
app.use(cookieParser());
app.use('/user', userRoutes);
app.use('/', authRoutes);


const PORT = process.env.PORT || 3000;
app.listen(PORT, (err) => {
    if (err) {
        console.log(err);
    } else {
        console.log(`Listening on PORT ${PORT}`);
    }
});