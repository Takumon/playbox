import { Action } from '@ngrx/store';

export enum CounterActionTypes {
  CounterIncrement = '[Counter] Increment count',
  CounterDecrement = '[Counter] Decrement count',
}

export class CountIncrement implements Action {
  readonly type = CounterActionTypes.CounterIncrement;
}

export class CountDecrement implements Action {
  readonly type = CounterActionTypes.CounterDecrement;
}


export type CounterActions =
  CountIncrement
  | CountDecrement;
