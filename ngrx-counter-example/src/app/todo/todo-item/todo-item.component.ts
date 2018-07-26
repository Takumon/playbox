import { Component, OnInit, Input, Output, EventEmitter, ChangeDetectionStrategy } from '@angular/core';
import { Todo } from '../../models/todo';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-todo-item',
  templateUrl: './todo-item.component.html',
  styleUrls: ['./todo-item.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class TodoItemComponent implements OnInit {
  @Input() loading: boolean;
  @Input() todo: Todo;
  @Output() update = new EventEmitter<Todo>();
  @Output() remove = new EventEmitter<string>();

  form: FormGroup;

  constructor(
    private fb: FormBuilder
  ) {
    this.form = this.fb.group({
      text: ['', Validators.required]
    });
  }

  ngOnInit() {
    this.form.patchValue({
      text: this.todo.text
    });
  }

  onSubmit() {
    const text = this.form.get('text').value;
    const todo = {...this.todo, text};
    this.update.emit(todo);
  }

}
