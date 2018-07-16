import { Component } from '@angular/core';
import { Talk } from '../model';
import { Backend } from '../backend';
import { ActivatedRoute } from '../../../node_modules/@angular/router';
import { mergeMap } from 'rxjs/operators';
import { WatchService } from '../watch';


@Component({
  selector: 'talk-details-cmp',
  templateUrl: './talk-details.component.html',
  styleUrls: ['./talk-details.component.css']
})
export class TalkDetailsComponent {
  talk: Talk;

  constructor(
    private backend: Backend,
    public watchService: WatchService,
    private route: ActivatedRoute
  ) {
    route.params
      .pipe(
        mergeMap(p => this.backend.findTalk(+p['id']))
      )
      .subscribe(t => this.talk = t);
  }

  handleRate(newRating: number): void {
    this.backend.rateTalk(this.talk.id, newRating);
  }

  handleWatch(): void {
    this.watchService.watch(this.talk);
  }

}
