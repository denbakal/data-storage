import {Component, OnInit} from '@angular/core';
import {PersonService} from "../person/person.service";
import {Person} from "../person/person.model";
import {Bucket} from "../shared/bucket.model";

@Component({
  selector: 'search',
  templateUrl: 'search.component.html'
})

export class SearchComponent implements OnInit {
  query: string = "";
  persons: Person[];
  total: number = 0;
  countries: Bucket[];
  years: Bucket[];

  constructor(private personService: PersonService) {
  }

  ngOnInit() {
    this.persons = [];
    this.countries = [];
    this.years = [];

    this.personService.getPersons()
      .subscribe((result) => {
          this.persons = result;
          this.total = result.length;
        }
      );
  }

  search() {
    this.persons = [];
    this.countries = [];
    this.years = [];

    this.personService.search(this.query)
      .subscribe((result) => {
          this.persons = result.persons;
          this.total = result.persons.length;
          this.countries = result.countries;
          this.years = result.years;
        }
      );
  }
}
