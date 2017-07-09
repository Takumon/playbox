import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpModule } from '@angular/http';
import { FormsModule,ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { 
  MdButtonModule,
  MdButtonToggleModule,
  MdChipsModule,
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
  MdTabsModule,
  MdProgressSpinnerModule,
  MdProgressBarModule,
  MdDialogModule,
  MdTooltipModule,
  MdSnackBarModule,
  MdPaginatorModule,
} from '@angular/material';

import { AppComponent, DialogResultExampleDialog } from './app.component';

@NgModule({
  declarations: [
    AppComponent,
    DialogResultExampleDialog,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpModule,
    FormsModule,
    ReactiveFormsModule,

    MdButtonModule,
    MdButtonToggleModule,
    MdChipsModule,
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
    MdProgressSpinnerModule,
    MdProgressBarModule,
    MdDialogModule,
    MdTooltipModule,
    MdSnackBarModule,
    MdPaginatorModule,
  ],
  providers: [],
  bootstrap: [AppComponent, DialogResultExampleDialog]
})
export class AppModule { }
