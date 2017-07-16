import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import 'rxjs/add/operator/map';
import {Observable} from 'rxjs/Observable';

@Injectable()
export class PostsService {

  constructor(private http: Http) { }

  getAllPosts(): Observable<Array<any>> {
    return this.http
                .get('/api/posts')
                .map(res => res.json());
  }

}
