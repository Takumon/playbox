import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LayoutModule } from '@angular/cdk/layout';
import {
  MatToolbarModule,
  MatButtonModule,
  MatSidenavModule,
  MatIconModule,
  MatListModule,
  MatGridListModule,
  MatCardModule,
  MatMenuModule,
  MatFormFieldModule,
  MatInputModule,
  MatCheckboxModule } from '@angular/material';
import { TalkComponent } from './talk/talk.component';
import { TalksComponent } from './talks/talks.component';
import { TalkDetailsComponent } from './talk-details/talk-details.component';
import { TalksAndFiltersComponent } from './talks-and-filters/talks-and-filters.component';
import { WatchButtonComponent } from './watch-button/watch-button.component';
import { RateButtonComponent } from './rate-button/rate-button.component';
import { FormatRatingPipe } from './pipes/format-rating.pipe';
import { FiltersComponent } from './filters/filters.component';
import { RouterModule } from '@angular/router';
import { Backend } from './backend';
import { WatchService } from './watch';
import { Store } from './store';
import { reducer, initState } from './model';



@NgModule({
  declarations: [
    AppComponent,
    TalkComponent,
    TalksComponent,
    TalkDetailsComponent,
    TalksAndFiltersComponent,
    WatchButtonComponent,
    RateButtonComponent,
    FormatRatingPipe,
    FiltersComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    LayoutModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    MatToolbarModule,
    MatButtonModule,
    MatSidenavModule,
    MatIconModule,
    MatListModule,
    MatGridListModule,
    MatCardModule,
    MatMenuModule,
    MatFormFieldModule,
    MatInputModule,
    MatCheckboxModule,
    RouterModule.forRoot([
      { path: '',  pathMatch: 'full', redirectTo: 'talks'},
      { path: 'talks',  component: TalksAndFiltersComponent },
      { path: 'talk/:id', component: TalkDetailsComponent }
    ])
  ],
  providers: [
    Backend,
    WatchService,
    {
      provide: Store,
      useFactory: (backend, watch) => new Store(reducer(backend, watch), initState),
      deps: [Backend, WatchService ]
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
