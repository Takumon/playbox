import { Injectable } from '@angular/core';
import { Hero } from './Hero';
import { HEROES } from './mock-heroes';


@Injectable()
export class HeroService {
  getHeroes(): Promise<Hero[]> {
    return Promise.resolve(HEROES);
  }

  getHeroesSlowly(): Promise<Hero[]> {
    return new Promise(resolve => {
      setTimeout(() => resolve(this.getHeroes()), 3000);
    });
  }

  getHero(id: number): Promise<Hero> {
    return this.getHeroes()
                    .then(heroes => heroes.find(hero => hero.id === id));
  }
}
