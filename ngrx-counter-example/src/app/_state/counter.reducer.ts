import { Action, ActionReducer, createFeatureSelector, createSelector } from '@ngrx/store';
import { types, Actions } from './counter.action';


export interface State {
  count: number;
}

export const initialState = {
  count: 0
};


export const reducer: ActionReducer<State> = (state: State = initialState, action: Action) => {
  switch (action.type) {
    case types.INCREMENT:
      return Object.assign({}, state, { count: state.count + 1});
    case types.DECREMENT:
      return Object.assign({}, state, { count: state.count - 1});
    default:
      return state;
  }
};


export const getState = createFeatureSelector<State>('counter');
export const getCount = createSelector(getState, state => state.count);
