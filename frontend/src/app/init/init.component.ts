import {Component, OnInit} from '@angular/core';
import {PersonService} from "../person/person.service";
import {isNumeric} from "rxjs/util/isNumeric";

@Component({
  selector: 'init-component',
  templateUrl: 'init.component.html'
})

export class InitComponent implements OnInit {
  persons: number;

  constructor(private personService: PersonService) {
  }

  ngOnInit() {
  }

  init(): void {
    if (this.persons && isNumeric(this.persons)) {
      this.personService.generatePersons(this.persons)
        .subscribe((status) => {
          if (status === 200) {
            console.log("Persons were be generated.");
          }
        });
    }
  }
}
