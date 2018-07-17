import { Observable, Subject, Observer, asyncScheduler, of } from 'rxjs';
import { mergeMap, map } from 'rxjs/operators';
import { observeOn } from 'rxjs/operators';
import { Injectable } from '@angular/core';



export type Reducer<S, A> = (store: Store<S, A>, state: S, action: A) => S|Observable<S>;


@Injectable()
export class Store<S, A> {
  private actions = new Subject<{action: A, result: Observer<boolean>}>();

  constructor(
    private reducer: Reducer<S, A>,
    public state: S
  ) {

    this.actions.pipe(
      observeOn(asyncScheduler),
      mergeMap(a => {
        const _state = reducer(this, this.state, a.action);
        const obs = _state instanceof Observable ? _state : of(_state);
        return obs.pipe(map(s => ({state: s, result: a.result})));
      })
    ).subscribe(pair => {
      this.state = pair.state;
      pair.result.next(true);
      pair.result.complete();
    });
  }

  sendAction(action: A): Observable<boolean> {
    const res = new Subject<boolean>();
    this.actions.next({action, result: res});
    return res;
  }
}
