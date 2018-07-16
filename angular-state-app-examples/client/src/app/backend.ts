import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Talk, Filters } from './model';
import { Observable, of } from 'rxjs';
import { map, catchError } from 'rxjs/operators';

@Injectable()
export class Backend {

  private url = 'http://localhost:4444';

  _talks: {[n: number]: Talk } = {};
  _list: number[] = [];


  filters: Filters = {
    speaker: null,
    title: null,
    minRating: 0
  };

  constructor(private http: HttpClient) {}

  get talks(): Talk[] {
    return this._list.map(n => this._talks[n]);
  }

  findTalk(id: number): Observable<Talk> {
    if (this._talks[id]) {
      return of(this._talks[id]);
    }

    const params = new HttpParams().set('id', id.toString());
    return this.http.get(`${this.url}/talk/`, { params }).pipe(map( (res: any) => {
      this._talks[id] = res.talk;
      return res.talk;
    }));
  }

  rateTalk(id: number, rating: number): void {
    const talk = this._talks[id];
    talk.yourRating = rating;
    this.http.post(`${this.url}/rate`, {
      id: talk.id,
      yourRating: rating
    })
    .pipe(
      catchError(e => {
        talk.yourRating = null;
        throw e;
      })
    ).forEach(() => {});
  }

  changeFilters(filters: Filters): void {
    this.filters = filters;
    this.refetch();
  }

  private refetch(): void {
    let params = new HttpParams();
    if (this.filters.speaker) {
      params = params.set('speaker', this.filters.speaker);
    }
    if (this.filters.title) {
      params = params.set('title', this.filters.title);
    }
    if (this.filters.minRating) {
      params = params.set('minRating', this.filters.minRating.toString());
    }

    this.http.get(`${this.url}/talks`, { params })
    .forEach( (res: any) => {
      this._talks = res.talks;
      this._list = res.list;
    });

  }
}
