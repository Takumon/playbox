import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Todo } from '../models/todo';
import { Observable, of } from 'rxjs';
import * as _ from 'lodash';

@Injectable({
  providedIn: 'root'
})
export class TodoService {

  // サーバー側は仮
  private todoNumbering = 4;
  private todos: Todo[] = [
    {
      id: '1',
      text: 'トイレ掃除',
      checked: false,
      createAt: 1532271802719,
      upadtedAt: 1532271802719,
    },
    {
      id: '2',
      text: '勉強',
      checked: false,
      createAt: 1532271802719,
      upadtedAt: 1532271802719,
    },
    {
      id: '3',
      text: 'ニュース見る',
      checked: false,
      createAt: 1532271802719,
      upadtedAt: 1532271802719,
    },
  ];

  constructor(
    private http: HttpClient
  ) { }

  findAll(offset: number, limit: number): Observable<Array<Todo>> {
    return of(this.todos);
  }

  findById(id: string): Observable<Todo> {
    return of(this.todos.filter(todo => todo.id === id)[0]);
  }

  create(todo: Todo): Observable<Todo> {
    todo.id = this.todoNumbering + '';
    this.todoNumbering++;
    const sysdate = new Date().getTime();
    todo.checked = false;
    todo.createAt = sysdate;
    todo.upadtedAt = sysdate;

    this.todos.push(todo);
    return of(todo);
  }

  update(todo: Todo): Observable<Todo> {
    const target = this.todos.filter(t => t.id === todo.id).pop();
    target.text = todo.text;
    target.checked = todo.checked;
    target.upadtedAt = new Date().getTime();
    return of(target);
  }

  // 削除したTodoを返す
  delete(id: string): Observable<Todo> {
    return of(_.remove(this.todos, todo => todo.id === id)[0]);
  }

}
