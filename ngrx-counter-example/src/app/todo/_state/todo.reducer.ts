
import { EntityState, EntityAdapter, createEntityAdapter } from '@ngrx/entity';

import { Todo } from '../../models/todo';
import { TodoActionsUnion, TodoActionTypes } from './todo.actions';

export interface State extends EntityState<Todo> {
  loading: boolean;
}

export const adapter: EntityAdapter<Todo> = createEntityAdapter<Todo>();

export const initialState: State = adapter.getInitialState({
  loading: false,
});


export function reducer(state = initialState, action: TodoActionsUnion): State {
  switch (action.type) {

    case TodoActionTypes.LoadTodos:
      return { ...state, loading: true };
    case TodoActionTypes.LoadTodosSuccess:
      return adapter.addAll(action.payload.todos, { ...state, loading: false});
    case TodoActionTypes.LoadTodosFail:
      return {...state, loading: false };

    case TodoActionTypes.CreateTodo:
      return { ...state, loading: true };
    case TodoActionTypes.CraeteTodoSuccess:
      return adapter.addOne(action.payload.todo, {...state, loading: false});
    case TodoActionTypes.CraeteTodoFail:
      return {...state, loading: false };

    case TodoActionTypes.UpdateTodo:
      return {...state, loading: true };
    case TodoActionTypes.UpdateTodoSuccess:
      return adapter.updateOne(action.payload.todo, {...state, loading: false});
    case TodoActionTypes.UpdateTodoFail:
      return {...state, loading: false };

    case TodoActionTypes.DeleteTodo:
      return {...state, loading: true };
    case TodoActionTypes.DeleteTodoSuccess:
      return adapter.removeOne(action.payload.id, {...state, loading: false});
    case TodoActionTypes.DeleteTodoFail:
      return {...state, loading: false };

    default:
      return state;
  }
}



export const getLoading = (state: State) => state.loading;
export const {
  selectIds,
  selectEntities,
  selectAll,
  selectTotal,
} = adapter.getSelectors();
