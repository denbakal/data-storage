import {Component, OnInit} from '@angular/core';
import {Person} from "../person/person.model";
import {PersonService} from "../person/person.service";

@Component({
  selector: 'advanced',
  templateUrl: 'advanced.component.html'
})

export class AdvancedComponent implements OnInit {
  name: string = "";
  country: string = "";
  city: string = "";
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

  advancedSearch() {
    this.persons = [];

    this.personService.advancedSearch(this.name, this.country, this.city)
      .subscribe((result) => {
          this.persons = result;
          this.total = result.length;
        }
      );
  }
}
