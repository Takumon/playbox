// Get dependencies
import * as path from 'path';
import * as http from 'http';
import * as bodyParser from 'body-parser';
import { Request, Response, Router } from "express";
import axios from 'axios';

const apiRouter: Router = Router();

// declare axios for making http requests
const API: string = 'https://jsonplaceholder.typicode.com';


/* GET api listing. */
apiRouter.get('/', (req: Request, res: Response) => {
  res.send('api works!!!!');
});

// Get all posts
apiRouter.get('/posts', (req: Request, res: Response) => {
  axios.get(`${API}/posts`)
    .then(posts => {
      res.status(200).json(posts.data);
    })
    .catch(error => {
      res.status(500).send(error)
    });
});

export { apiRouter };