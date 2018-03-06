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
import {CassandraValueComponent} from "./store-data/cassandra/cassandra-value.component";
import {RedisValueComponent} from "./store-data/redis/redis-value.component";
import {MongoValueComponent} from "./store-data/mongo/mongo-value.component";

const routes: Routes = [
  { path: '', redirectTo: '/store/json-value', pathMatch: 'full' },
  { path: 'store/json-value', component: JsonValueComponent },
  { path: 'store/cell-value', component: CellValueComponent },
  { path: 'store/cassandra', component: CassandraValueComponent },
  { path: 'store/redis', component: RedisValueComponent },
  { path: 'store/mongo', component: MongoValueComponent }
];

@NgModule({
  declarations: [
    AppComponent,
    JsonValueComponent,
    CellValueComponent,
    CassandraValueComponent,
    RedisValueComponent,
    MongoValueComponent
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
