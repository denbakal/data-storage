import {Component, OnInit} from '@angular/core';
import {BaseFieldService} from "../../base-field/base-field.service";
import {isNumeric} from "rxjs/util/isNumeric";

@Component({
  selector: 'json-value',
  templateUrl: 'json-value.component.html'
})

export class JsonValueComponent implements OnInit {
  static BASE_TABLE_ID: number = 1;
  static DEFAULT_LANE_COUNT: number = 15000;

  count: number;
  data: object[] = [];
  // columns: object[] = [];
  settings: object = {
    afterLoadData: (firstLoad) => {
      if(!firstLoad) {
        this.isLoading = false;
      }
    },
    colHeaders: true,
    columns: [
      {data: 'fields.FROM', type: 'text', title: 'FROM'},
      {data: 'fields.TO', type: 'text', title: 'TO'},
      {data: 'fields.SERVICE', type: 'text', title: 'SERVICE'},

      {data: 'prices.81.FIRST', type: 'text', title: 'P1.FIRST'},
      {data: 'prices.81.SECOND', type: 'text', title: 'P1.SECOND'},
      {data: 'prices.81.THIRD', type: 'text', title: 'P1.THIRD'},
      {data: 'prices.174.FIRST', type: 'text', title: 'P2.FIRST'},
      {data: 'prices.174.SECOND', type: 'text', title: 'P2.SECOND'},
      {data: 'prices.174.THIRD', type: 'text', title: 'P2.THIRD'}
    ],
    rowHeaders: true
  };
  isLoading: boolean = false;
  isSaving: boolean = false;
  prices: number[] = [81, 174];

  constructor(private baseFieldService: BaseFieldService) {}

  ngOnInit(): void {
    this.initData();
  }

  initData() {
    /*this.baseFieldService
      .getFieldsByBaseTableId(JsonValueComponent.BASE_TABLE_ID)
      .subscribe((fields) => {
        console.log('Count of getting fields: ' + fields.length);
        this.columns = [];

        if (fields.length === 0) {
          // this.data = [];
          // this.count = null;
          for (var i = 0; i < 10; i++) {
            this.columns.push({data: `fields.f${i}`, title: `F${i}`});
          }
        } else {
          fields.forEach(f => {
            this.columns.push({data: f.name.toLowerCase(), title: f.name});
          })
        }
      });*/

    /*for (let i = 0; i < 5; i++) {
      this.columns.push({data: `fields.f${i}`, title: `F${i}`});
    }*/

    /*this.baseFieldService
      .getFieldValuesByBaseTableId(JsonValueComponent.BASE_TABLE_ID, 'JSON')
      .subscribe((data) => {
        console.log('Count of getting data: ' + data.length);
        this.data = [];

        if (data.length == 0) {
          for (let i = 0; i < JsonValueComponent.DEFAULT_LANE_COUNT; i++) {
            this.data.push({});
          }
        } else {
          data.forEach(element => {
            this.data.push(JSON.parse(element));
          });
        }
      });*/
  }

  loadData() {
    this.isLoading = true;
    this.data = [];
    for (let i = 0; i < JsonValueComponent.DEFAULT_LANE_COUNT; i++) {
      this.data.push({"fields": {"FROM":"345","TO":"asdaf","SERVICE":"sdferverve" + i},
        "prices": {"81": {"FIRST":"1", "SECOND":"2", "THIRD":"3"}, "174": {"FIRST":"4", "SECOND":"5", "THIRD":"6"}}});
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
    this.baseFieldService.storeData(array, 'JSON')
      .subscribe((status) => {
        if (status === 200) {
          this.isSaving = false;
        }
      });
  }

  removeData() {
    this.baseFieldService.removeData(JsonValueComponent.BASE_TABLE_ID, 'JSON')
      .subscribe((status) => {
        if (status === 200) {
          this.initData();
        }
      });
  }
}
