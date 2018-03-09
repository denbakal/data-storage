import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HotTableModule } from 'angular-handsontable';

import { AppComponent } from './app.component';
import {RouterModule, Routes} from "@angular/router";
import {HttpClientModule} from "@angular/common/http";
import {FormsModule} from "@angular/forms";
import {InitComponent} from "./init/init.component";
import {SearchComponent} from "./search/search.component";
import {PersonService} from "./person/person.service";
import {AdvancedComponent} from "./advanced/advanced.component";

const routes: Routes = [
  { path: '', redirectTo: '/search', pathMatch: 'full' },
  { path: 'init', component: InitComponent },
  { path: 'search', component: SearchComponent },
  { path: 'advanced', component: AdvancedComponent }
];

@NgModule({
  declarations: [
    AppComponent,
    InitComponent,
    SearchComponent,
    AdvancedComponent
  ],
  imports: [
    BrowserModule,
    HotTableModule,
    HttpClientModule,
    FormsModule,
    RouterModule.forRoot(routes, {useHash: true})
  ],
  providers: [
    PersonService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
