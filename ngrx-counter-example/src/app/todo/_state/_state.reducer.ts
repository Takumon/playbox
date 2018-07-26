import {
  ActionReducerMap, createFeatureSelector, createSelector,
} from '@ngrx/store';
import * as FromCounter from './counter.reducer';
import * as FromTodo from './todo.reducer';

export interface State {
  todos: FromTodo.State;
  counter: FromCounter.State;
}

export const reducers: ActionReducerMap<State> = {
  todos: FromTodo.reducer,
  counter: FromCounter.reducer
};

export const getTodoFeatureState = createFeatureSelector<State>('todo');
export const getTodoEntityState = createSelector(getTodoFeatureState, state => state.todos);
export const getTodos = createSelector(getTodoEntityState, FromTodo.selectAll);
export const getLoading = createSelector(getTodoEntityState, FromTodo.getLoading);
