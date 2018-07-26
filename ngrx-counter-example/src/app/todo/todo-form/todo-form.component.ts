import { Component, Input, Output, EventEmitter, ChangeDetectionStrategy } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-todo-form',
  templateUrl: './todo-form.component.html',
  styleUrls: ['./todo-form.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class TodoFormComponent {
  @Input() loading: boolean;
  @Output() create = new EventEmitter<string>();

  form: FormGroup;


  constructor(
    private fb: FormBuilder
  ) {
    this.form = this.fb.group({
      text: ['', Validators.required]
    });
  }
}
