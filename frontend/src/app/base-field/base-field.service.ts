import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {BaseField} from "./base-field.model";
import 'rxjs/add/operator/map';

@Injectable()
export class BaseFieldService {
  constructor(private http: HttpClient) {
  }

  public getFieldsByBaseTableId(baseTableId: number): Observable<BaseField[]> {
    return this.http.get(`/base-tables/${baseTableId}/fields`)
                    .map((response) => response as BaseField[]);
  }

  public getFieldValuesByBaseTableId(baseTableId: number, type: string): Observable<any[]> {
    return this.http.get(`/base-tables/${baseTableId}/field-values?type=${type}`)
      .map((response) => response as any[]);
  }

  public generateColumns(count: number): Observable<number> {
    return this.http.post(`/fields?generate=1&count=${count}`, null, {observe: 'response', responseType: 'text'})
             .map(response => response.status as number)
  }

  public storeData(data: any[], type: string): Observable<number> {
    return this.http.post(`/field-values?type=${type}`, data, {observe: 'response', responseType: 'text'})
      .map(response => response.status as number)
  }

  public remove(): Observable<number> {
    return this.http.delete('/fields', {observe: 'response', responseType: 'text'})
      .map(response => response.status as number);
  }

  public removeData(baseTableId: number, type: string): Observable<number> {
    return this.http.delete(`/base-tables/${baseTableId}/field-values?type=${type}`, {observe: 'response', responseType: 'text'})
        .map(response => response.status as number);
  }
}
