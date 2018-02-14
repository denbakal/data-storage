import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HotTableModule } from 'angular-handsontable';

import { AppComponent } from './app.component';


@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    HotTableModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
