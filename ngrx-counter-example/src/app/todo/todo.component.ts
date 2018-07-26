import { Component, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

import * as TodoReducer from './_state/todo.reducer';
import * as StateReducer from './_state/_state.reducer';

import { Observable, Subject } from 'rxjs';
import { Todo } from '../models/todo';
import {
  LoadTodos,
  CreateTodo,
  UpdateTodo,
  DeleteTodo
} from './_state/todo.actions';

@Component({
  selector: 'app-todo',
  templateUrl: './todo.component.html',
  styleUrls: ['./todo.component.css']
})
export class TodoComponent implements OnInit {

  loading$: Observable<boolean>;
  todos$: Observable<Todo[]>;
  todoForm: FormGroup;
  onDestroy = new Subject();

  constructor(
    private store: Store<TodoReducer.State>,
    private fb: FormBuilder
  ) {

    this.todoForm = this.fb.group({
      text: ['', Validators.required]
    });
    this.loading$ = this.store.select(StateReducer.getLoading);
    this.todos$ = this.store.select(StateReducer.getTodos);
  }

  ngOnInit() {
    this.store.dispatch(new LoadTodos());
  }

  onCreate(text: string) {
    this.store.dispatch(new CreateTodo({
      todo: new Todo(null, text)
    }));
  }

  onUpdate(todo: Todo) {
    this.store.dispatch(new UpdateTodo({
      todo: {
        id: todo.id,
        changes: todo
      }
    }));
  }

  onRemove(id: string) {
    this.store.dispatch(new DeleteTodo({ id }));
  }

}
