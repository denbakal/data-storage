import {Component, OnInit} from '@angular/core';
import {BaseFieldService} from "../../base-field/base-field.service";
import {isNumeric} from "rxjs/util/isNumeric";

@Component({
  selector: 'redis-value',
  templateUrl: 'redis-value.component.html'
})

export class RedisValueComponent implements OnInit {
  static BASE_TABLE_ID: number = 1;
  static DEFAULT_LANE_COUNT: number = 15000;

  count: number;
  data: object[] = [];
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
    for (let i = 0; i < RedisValueComponent.DEFAULT_LANE_COUNT; i++) {
      this.data.push({FROM:"DE" + i, TO:"UA", SERVICE:"Standard"});
    }
    this.isLoading = false;
  }

  generateColumns() {
    if (this.count && isNumeric(this.count)) {
      this.baseFieldService.generateColumns(this.count)
        .subscribe((status) => {
          if (status === 200) {
            this.initData();
          }
        });
    }
  }

  remove() {
    this.baseFieldService.remove()
      .subscribe((status) => {
        if (status === 200) {
          this.initData();
        }
      });
  }

  saveData() {
    this.isSaving = true;
    let array: any[] = [];
    this.data.forEach(d => {
      array.push(JSON.stringify(d));
    });
    this.baseFieldService.storeRedisData(array)
      .subscribe((status) => {
        if (status === 200) {
          this.isSaving = false;
        }
      });
  }

  removeData() {
  }
}
