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
  columns: object[] = [];
  settings: object = {
    afterLoadData: (firstLoad) => {
      if(!firstLoad) {
        this.isLoading = false;
      }
    },
  };
  isLoading: boolean = false;
  isSaving: boolean = false;

  constructor(private baseFieldService: BaseFieldService) {}

  ngOnInit(): void {
    this.initData();
  }

  initData() {
    this.baseFieldService
      .getFieldsByBaseTableId(JsonValueComponent.BASE_TABLE_ID)
      .subscribe((fields) => {
        console.log('Count of getting fields: ' + fields.length);
        this.columns = [];

        if (fields.length === 0) {
          this.data = [];
          this.count = null;
        } else {
          fields.forEach(f => {
            this.columns.push({data: f.name.toLowerCase(), title: f.name});
          })
        }
      });

    this.baseFieldService
      .getFieldValuesByBaseTableId(JsonValueComponent.BASE_TABLE_ID)
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
      });
  }

  loadData() {
    this.isLoading = true;
    this.data = [];
    for (let i = 0; i < JsonValueComponent.DEFAULT_LANE_COUNT; i++) {
      this.data.push({"0":"345","1":"asdaf","2":"sdferverve","3":"er33f3","4":"adwf343","5":"adwf343","6":"adwf343","7":"adwf343","8":"adwf343","9":"adwf343","10":"adwf343","11":"adwf343","12":"adwf343","13":"adwf343","14":"adwf343","15":"adwf343","16":"adwf343","17":"adwf343","18":"adwf343","19":"adwf343"});
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
    this.baseFieldService.storeData(array)
      .subscribe((status) => {
        if (status === 200) {
          this.isSaving = false;
        }
      });
  }

  removeData() {
    this.baseFieldService.removeData(JsonValueComponent.BASE_TABLE_ID)
      .subscribe((status) => {
        if (status === 200) {
          this.initData();
        }
      });
  }
}
