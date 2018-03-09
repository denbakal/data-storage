import {Component, OnInit} from '@angular/core';
import {PersonService} from "../person/person.service";
import {Person} from "../person/person.model";

@Component({
  selector: 'search',
  templateUrl: 'search.component.html'
})

export class SearchComponent implements OnInit {
  query: string;
  persons: Person[];
  total: number = 0;

  constructor(private personService: PersonService) {
  }

  ngOnInit() {
    this.persons = [];

    this.personService.getPersons()
      .subscribe((result) => {
          this.persons = result;
          this.total = result.length;
        }
      );
  }
}
