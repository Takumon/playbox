import {
  ActionReducerMap,
} from '@ngrx/store';
import * as FromCounter from './counter.reducer';

export interface State {
  counter: FromCounter.State;
}

export const reducers: ActionReducerMap<State> = {
  counter: FromCounter.reducer
};
