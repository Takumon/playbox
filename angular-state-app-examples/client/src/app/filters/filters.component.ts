import { Component, Output, EventEmitter, Input } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { debounceTime } from 'rxjs/operators';

@Component({
  selector: 'filters-cmp',
  templateUrl: './filters.component.html',
  styleUrls: ['./filters.component.css']
})
export class FiltersComponent {
  @Output() filtersChange = new EventEmitter();

  @Input() set filters(v) {
    this.filtersForm.setValue({
      title: v.title,
      speaker: v.speaker,
      highRating: v.minRating >= 9
    }, {
      emitEvent: false
    });
  }

  public filtersForm: FormGroup = new FormGroup({
    speaker: new FormControl(),
    title: new FormControl(),
    highRating: new FormControl(false),
  });

  constructor() {
    this.filtersForm.valueChanges.pipe(debounceTime(200)).subscribe(value => {
      const filters = {
        speaker: value.speaker || null,
        title: value.title || null,
        minRating: value.highRating ? 9 : 0
      };
      this.filtersChange.next(filters);
    });
  }
}
