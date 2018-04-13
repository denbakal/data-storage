import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {Person} from "./person.model";
import 'rxjs/add/operator/map';

@Injectable()
export class PersonService {

  constructor(private http: HttpClient) {
  }

  public getPersons(): Observable<Person[]> {
    return this.http.get(`/persons`)
      .map((response) => response as Person[]);
  }

  generatePersons(persons: number): Observable<number> {
    let headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.put('/persons', persons, {headers: headers, observe: 'response', responseType: 'json'})
      .map(response => response.status as number);
  }

  advancedSearch(name: string, country: string, city: string): Observable<Person[]> {
    return this.http.get(`/persons/advanced-search?name=${name}&country=${country}&city=${city}`)
      .map(response => response as Person[]);
  }

  search(query: string): Observable<Person[]> {
    return this.http.get(`/persons/search?query=${query}`)
      .map(response => response as Person[]);
  }
}
