import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpModule } from '@angular/http';
import { FormsModule,ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { 
  MdButtonModule,
  MdCardModule,
  MdMenuModule,
  MdToolbarModule,
  MdIconModule,
  MdSidenavModule,
  MdInputModule,
  MdAutocompleteModule,
  MdCheckboxModule,
  MdRadioModule,
  MdDatepickerModule,
  MdNativeDateModule,
  MdSelectModule,
  MdSliderModule,
  MdSlideToggleModule,
  MdListModule,
  MdGridListModule,
  MdTabsModule
} from '@angular/material';

import { AppComponent } from './app.component';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MdButtonModule,
    MdCardModule,
    MdMenuModule,
    MdToolbarModule,
    MdIconModule,
    MdSidenavModule,
    MdAutocompleteModule,
    MdInputModule,
    MdCheckboxModule,
    MdRadioModule,
    MdDatepickerModule,
    MdNativeDateModule,
    MdSelectModule,
    MdSliderModule,
    MdSlideToggleModule,
    MdListModule,
    MdGridListModule,
    MdTabsModule,
    HttpModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
