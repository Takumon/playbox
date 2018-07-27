import {
  ActionReducerMap, createFeatureSelector, createSelector,
} from '@ngrx/store';
import * as FromTodo from './todo.reducer';

export interface State {
  todos: FromTodo.State;
}

export const reducers: ActionReducerMap<State> = {
  todos: FromTodo.reducer,
};

export const getTodoFeatureState = createFeatureSelector<State>('todo');
export const getTodoEntityState = createSelector(getTodoFeatureState, state => state.todos);
export const getTodos = createSelector(getTodoEntityState, FromTodo.selectAll);
export const getLoading = createSelector(getTodoEntityState, FromTodo.getLoading);
