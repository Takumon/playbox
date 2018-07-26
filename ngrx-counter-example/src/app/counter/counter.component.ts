import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Store } from '@ngrx/store';

import * as CounterReducer from '../todo/_state/counter.reducer';
import * as CounterAction from '../todo/_state/counter.action';

@Component({
  selector: 'app-counter',
  templateUrl: './counter.component.html',
  styleUrls: ['./counter.component.css']
})
export class CounterComponent implements OnInit {

  count$: Observable<number>;

  constructor(
    private store: Store<CounterReducer.State>
  ) {
    this.count$ = this.store.select(CounterReducer.getCount);
  }

  ngOnInit() {
  }

  increment() {
    this.store.dispatch(new CounterAction.Increment());
  }

  incrementAsync() {
    setTimeout(() => this.store.dispatch(new CounterAction.Increment()), 1000);
  }

  decrement() {
    this.store.dispatch(new CounterAction.Decrement());
  }

  decrementAsync() {
    setTimeout(() => this.store.dispatch(new CounterAction.Decrement()), 1000);
  }


}
