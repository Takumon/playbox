import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Talk, Filters } from './model';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable()
export class Backend {

  private url = 'http://localhost:4444';

  constructor(private http: HttpClient) {}

  findTalks(filters: Filters): Observable<{talks: {[id: number]: Talk}, list: number[]}> {
    let params = new HttpParams();
    if (filters.speaker) {
      params = params.set('speaker', filters.speaker);
    }
    if (filters.title) {
      params = params.set('title', filters.title);
    }
    if (filters.minRating) {
      params = params.set('minRating', filters.minRating.toString());
    }

    return this.http.get<{talks: {[id: number]: Talk}, list: number[]}> (`${this.url}/talks`, { params });
  }

  findTalk(id: number): Observable<Talk> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.get(`${this.url}/talk/`, { params }).pipe(map( (res: any) => res.talk));
  }

  rateTalk(id: number, rating: number): Observable<Talk> {
    return this.http.post<Talk>(`${this.url}/rate`, { id, yourRating: rating });
  }
}
