import { Injectable } from '@angular/core';
import { Actions, Effect, ofType } from '@ngrx/effects';
import { Action } from '@ngrx/store';

import { TodoService } from '../../services/todo.service';
import { Observable, of } from 'rxjs';
import { map, catchError, switchMap, concatMap } from 'rxjs/operators';
import {
  TodoActionTypes,
  LoadTodos,
  LoadTodosSuccess,
  LoadTodosFail,
  CreateTodo,
  CreateTodoSuccess,
  CreateTodoFail,
  DeleteTodo,
  DeleteTodoSuccess,
  DeleteTodoFail,
  UpdateTodo,
  UpdateTodoSuccess,
  UpdateTodoFail
} from './todo.actions';


@Injectable()
export class TodoEffects {

  constructor(
    private actions$: Actions,
    private todoService: TodoService
  ) {}

  @Effect()
  loadTodos$: Observable<Action> = this.actions$.pipe(
    ofType<LoadTodos>(TodoActionTypes.LoadTodos),
    switchMap(action =>
      this.todoService
        .findAll(action.payload.offset, action.payload.limit)
        .pipe(
          map(todos => new LoadTodosSuccess({ todos })),
          catchError(error => of(new LoadTodosFail({ error})))
        )
    )
  );

  @Effect()
  createTodo$: Observable<Action> = this.actions$.pipe(
    ofType<CreateTodo>(TodoActionTypes.CreateTodo),
    concatMap(action =>
      this.todoService.create(action.payload.todo).pipe(
        map(todo => new CreateTodoSuccess({ todo })),
        catchError(error => of(new CreateTodoFail({ error })))
      )
    )
  );


  @Effect()
  updateTodo$: Observable<Action> = this.actions$.pipe(
    ofType<UpdateTodo>(TodoActionTypes.UpdateTodo),
    concatMap(action =>
      this.todoService
        .update({...action.payload.todo.changes})
        .pipe(
          map(data => new UpdateTodoSuccess({ todo: { id: data.id, changes: data } })),
          catchError(error => of(new UpdateTodoFail({ error })))
        )
    )
  );

  @Effect()
  deleteTodo$: Observable<Action> = this.actions$.pipe(
    ofType<DeleteTodo>(TodoActionTypes.DeleteTodo),
    concatMap(action =>
      this.todoService.delete(action.payload.id).pipe(
        map(() => new DeleteTodoSuccess({id: action.payload.id})),
        catchError(error => of(new DeleteTodoFail({ error })))
      )
    )
  );

}
