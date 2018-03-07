import {Component, OnInit} from '@angular/core';
import {BaseFieldService} from "../../base-field/base-field.service";
import {isNumeric} from "rxjs/util/isNumeric";

@Component({
  selector: 'cassandra-value',
  templateUrl: 'cassandra-value.component.html'
})

export class CassandraValueComponent implements OnInit {
  static BASE_TABLE_ID: number = 1;
  static DEFAULT_LANE_COUNT: number = 15000;

  count: number;
  data: object[] = [{}];

  settings: object = {
    afterLoadData: (firstLoad) => {
      if(!firstLoad) {
        this.isLoading = false;
      }
    },
    colHeaders: true,
    columns: [
      {data: 'FROM', type: 'text', title: 'FROM'},
      {data: 'TO', type: 'text', title: 'TO'},
      {data: 'SERVICE', type: 'text', title: 'SERVICE'}
    ],
    rowHeaders: true
  };
  isLoading: boolean = false;
  isSaving: boolean = false;

  constructor(private baseFieldService: BaseFieldService) {}

  ngOnInit(): void {
    this.initData();
  }

  initData() {
  }

  loadData() {
    this.isLoading = true;
    this.data = [];
    // for (let i = 0; i < 5; i++) {
    for (let i = 0; i < CassandraValueComponent.DEFAULT_LANE_COUNT; i++) {
      this.data.push({FROM:"DE", TO:"UA", SERVICE:"Standard"});
    }
    this.isLoading = false;
  }

  saveData(type: string) {
    this.isSaving = true;
    let array: any[] = [];
    console.log(this.data);

    this.baseFieldService.storeCassandraData(this.data, type)
      .subscribe((status) => {
        if (status === 200) {
          this.isSaving = false;
        }
      });
  }

  removeData() {
  }
}
