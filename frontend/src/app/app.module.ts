import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HotTableModule } from 'angular-handsontable';

import { AppComponent } from './app.component';
import {HttpClientModule} from "@angular/common/http";
import {BaseFieldService} from "./base-field/base-field.service";
import {JsonValueComponent} from "./store-data/json/json-value.component";
import {RouterModule, Routes} from "@angular/router";
import {FormsModule} from "@angular/forms";
import {CellValueComponent} from "./store-data/cell/cell-value.component";

const routes: Routes = [
  { path: '', redirectTo: '/store/json-value', pathMatch: 'full' },
  { path: 'store/json-value', component: JsonValueComponent },
  { path: 'store/cell-value', component: CellValueComponent }
];

@NgModule({
  declarations: [
    AppComponent,
    JsonValueComponent,
    CellValueComponent
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
