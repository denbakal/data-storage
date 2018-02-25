import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HotTableModule } from 'angular-handsontable';

import { AppComponent } from './app.component';
import {HttpClientModule} from "@angular/common/http";
import {BaseFieldService} from "./base-field/base-field.service";
import {JsonValueComponent} from "./store-data/json/json-value.component";
import {RouterModule, Routes} from "@angular/router";
import {FormsModule} from "@angular/forms";

const routes: Routes = [
  { path: '', redirectTo: '/store/json-value', pathMatch: 'full' },
  { path: 'store/json-value', component: JsonValueComponent }
];

@NgModule({
  declarations: [
    AppComponent,
    JsonValueComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    HotTableModule,
    FormsModule,
    RouterModule.forRoot(routes, {useHash: true})
  ],
  providers: [
    BaseFieldService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
