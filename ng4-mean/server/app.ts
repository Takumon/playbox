// Get dependencies
import * as express from 'express';
import * as path from 'path';
import * as http from 'http';
import * as bodyParser from 'body-parser';
import axios from 'axios';
import { apiRouter } from './routes/api';


// declare axios for making http requests
const API = 'https://jsonplaceholder.typicode.com';

const app: express.Application = express();

// Parsers for POST data
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));

// Point static path to dist
app.use(express.static(path.join(__dirname, 'dist')));

// Set our api routes
app.use('/api', apiRouter);

// Catch all other routes and return the index file
app.get('*', (req, res) => {
  res.sendFile(path.join(__dirname, '../client/index.html'));
});

export { app }
