import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Store } from '@ngrx/store';

import * as CounterReducer from './_state/counter.reducer';
import { CountIncrement, CountDecrement } from './_state/counter.actions';
import { getCount } from './_state/_state.reducer';

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
    this.count$ = this.store.select(getCount);
  }

  ngOnInit() {
  }

  increment() {
    this.store.dispatch(new CountIncrement());
  }

  incrementAsync() {
    setTimeout(() => this.store.dispatch(new CountIncrement()), 1000);
  }

  decrement() {
    this.store.dispatch(new CountDecrement());
  }

  decrementAsync() {
    setTimeout(() => this.store.dispatch(new CountDecrement()), 1000);
  }
}
