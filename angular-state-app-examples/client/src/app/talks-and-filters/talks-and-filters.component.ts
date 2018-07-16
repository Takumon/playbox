import { Component } from '@angular/core';
import { Backend } from '../backend';
import { Filters } from '../model';
import { Router, ActivatedRoute, Params } from '@angular/router';


@Component({
  selector: 'app-cmp',
  templateUrl: './talks-and-filters.component.html',
  styleUrls: ['./talks-and-filters.component.css']
})
export class TalksAndFiltersComponent {

  constructor(
    public backend: Backend,
    private router: Router,
    private route: ActivatedRoute
  ) {
    route.params.subscribe(p => {
      const filters = {
        speaker: p.speaker || null,
        title: p.title || null,
        minRating: p.minRating ? p.minRating : 0
      };

      this.backend.changeFilters(filters);
    });

  }

  handleFiltersChange(filters: Filters): void {
    this.backend.changeFilters(filters);
    this.router.navigate(['/talks', this.createParams(filters)]);
  }

  private createParams(filters: Filters): Params {
    const r: any = {};
    if (filters.speaker) {
      r.speaker = filters.speaker;
    }
    if (filters.title) {
      r.title = filters.title;
    }
    if (filters.minRating) {
      r.minRating = filters.minRating;
    }
    return r;
  }
}
