import { Injectable }    from '@angular/core';
import { Headers, Http } from '@angular/http';

import 'rxjs/add/operator/toPromise';

import { Hero } from './Hero';


@Injectable()
export class HeroService {
  private heroesUrl = 'api/heroes';
  private headers = new Headers({'Content-Type': 'applicaiton/json'});

  constructor(private http: Http) {}


  getHeroes(): Promise<Hero[]> {
    return this.http.get(this.heroesUrl)
             .toPromise()
             .then(response => response.json().data as Hero[])
             .catch(this.handleError);
  }


  getHero(id: number): Promise<Hero> {
    const url = `${this.heroesUrl}/${id}`;
    return this.http.get(url)
                    .toPromise()
                    .then(response => response.json().data as Hero)
                    .catch(this.handleError);
  }

  update(hero : Hero): Promise<Hero> {
    const url = `${this.heroesUrl}/${hero.id}`;
    return this.http.put(url, JSON.stringify(hero), {headers: this.headers})
                    .toPromise()
                    .then(() => hero)
                    .catch(this.handleError);
  }

  delete(hero : Hero): Promise<Hero> {
    const url = `${this.heroesUrl}/${hero.id}`;
    return this.http.delete(url, {headers: this.headers})
                    .toPromise()
                    .then(() => null)
                    .catch(this.handleError);
  }

  create(name: String): Promise<Hero> {
    return this.http.post(this.heroesUrl, JSON.stringify({name: name}), {headers: this.headers})
                    .toPromise()
                    .then(res => res.json().data as Hero)
                    .catch(this.handleError);
  }


  private handleError(error: any): Promise<any> {
    console.error('An error occoured', error);

    return Promise.reject(error.message || error);
  }
}
