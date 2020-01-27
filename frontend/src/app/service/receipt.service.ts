import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Receipt} from "../model/receipt";

@Injectable({
  providedIn: 'root'
})
export class ReceiptService {
  private MAIN_URL = "http://localhost:8080/api/receipt";
  private GET_ALL_FUEL_URL = `${this.MAIN_URL}/all`;

  constructor(private http: HttpClient) { }

  getAllFuel(): Observable<Receipt[]> {
    return this.http.get<Receipt[]>(this.GET_ALL_FUEL_URL);
  }
}
