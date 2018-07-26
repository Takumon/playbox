import { Action } from '@ngrx/store';


export enum types {
  INCREMENT = 'INCREMENT',
  DECREMENT = 'DECREMENT'
}

export class Increment implements Action {
  readonly type = types.INCREMENT;
}

export class Decrement implements Action {
  readonly type = types.DECREMENT;
}

export type Actions = Increment | Decrement;
