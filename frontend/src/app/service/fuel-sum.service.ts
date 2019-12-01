import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {FuelSum} from "../model/fuel-sum";

@Injectable({
  providedIn: 'root'
})
export class FuelSumService {
  private MAIN_URL = "http://localhost:8080/api/fuel";
  private GET_LAST_FUEL_URL = `${this.MAIN_URL}/last`;

  constructor(private http: HttpClient) { }

  getLastFuel(): Observable<FuelSum> {
    return this.http.get<FuelSum>(this.GET_LAST_FUEL_URL);
  }
}
