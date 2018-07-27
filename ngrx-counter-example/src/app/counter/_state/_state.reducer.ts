import { Action, ActionReducerMap, createFeatureSelector, createSelector } from '@ngrx/store';
import * as FromCounter from './counter.reducer';

export interface State {
  counter: FromCounter.State;
}

export const reducers: ActionReducerMap<State> = {
  counter: FromCounter.reducer
};


export const getCounterFeatureState = createFeatureSelector<State>('counter');
export const getCounter = createSelector(getCounterFeatureState, s => s.counter);
export const getCount = createSelector(getCounter, FromCounter.getCount);
