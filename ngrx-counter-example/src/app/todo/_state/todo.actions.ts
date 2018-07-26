import { Action } from '@ngrx/store';
import { Update } from '@ngrx/entity';

import { Todo } from '../../models/todo';


export enum TodoActionTypes {
  LoadTodos         = '[Todo] Load',
  LoadTodosSuccess  = '[Todo] Load Success',
  LoadTodosFail     = '[Todo] Load Fail',
  CreateTodo        = '[Todo] Create',
  CraeteTodoSuccess = '[Todo] Create Success',
  CraeteTodoFail    = '[Todo] Create Fail',
  UpdateTodo        = '[Todo] Update',
  UpdateTodoSuccess = '[Todo] Update Success',
  UpdateTodoFail    = '[Todo] Update Fail',
  DeleteTodo        = '[Todo] Delete',
  DeleteTodoSuccess = '[Todo] Delete Success',
  DeleteTodoFail    = '[Todo] Delete Fail',
}

export class LoadTodos implements Action {
  readonly type = TodoActionTypes.LoadTodos;
  constructor(public payload: {offset: number, limit: number} = {offset: 0, limit: 100}) {
  }
}

export class LoadTodosSuccess implements Action {
  readonly type = TodoActionTypes.LoadTodosSuccess;
  constructor(public payload: { todos: Todo[] }) {}
}

export class LoadTodosFail implements Action {
  readonly type = TodoActionTypes.LoadTodosFail;
  constructor(public payload?: { error: any }) {}
}

export class CreateTodo implements Action {
  readonly type = TodoActionTypes.CreateTodo;
  constructor(public payload: { todo: Todo }) {}
}

export class CreateTodoSuccess implements Action {
  readonly type = TodoActionTypes.CraeteTodoSuccess;
  constructor(public payload: { todo: Todo }) {}
}

export class CreateTodoFail implements Action {
  readonly type = TodoActionTypes.CraeteTodoFail;
  constructor(public payload?: { error: any }) {}
}


export class UpdateTodo implements Action {
  readonly type = TodoActionTypes.UpdateTodo;
  constructor(public payload: { todo: Update<Todo> }) {}
}

export class UpdateTodoSuccess implements Action {
  readonly type = TodoActionTypes.UpdateTodoSuccess;
  constructor(public payload: { todo: Update<Todo> }) {}
}

export class UpdateTodoFail implements Action {
  readonly type = TodoActionTypes.UpdateTodoFail;
  constructor(public payload?: { error: any }) {}
}

export class DeleteTodo implements Action {
  readonly type = TodoActionTypes.DeleteTodo;
  constructor(public payload: { id: string}) {}
}

export class DeleteTodoSuccess implements Action {
  readonly type = TodoActionTypes.DeleteTodoSuccess;
  constructor(public payload?: { id: string}) {}
}

export class DeleteTodoFail implements Action {
  readonly type = TodoActionTypes.DeleteTodoFail;
  constructor(public payload?: { error: any}) {}
}


export type TodoActionsUnion =
  LoadTodos
  | LoadTodosSuccess
  | LoadTodosFail
  | CreateTodo
  | CreateTodoSuccess
  | CreateTodoFail
  | UpdateTodo
  | UpdateTodoSuccess
  | UpdateTodoFail
  | DeleteTodo
  | DeleteTodoSuccess
  | DeleteTodoFail;
