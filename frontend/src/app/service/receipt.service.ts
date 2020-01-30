import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Receipt} from "../model/receipt";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class ReceiptService {
  private RECEIPT_URL = environment.mainUrl+"/api/receipt";
  private GET_ALL_FUEL_URL = `${this.RECEIPT_URL}/all`;

  constructor(private http: HttpClient) { }

  getAllFuel(): Observable<Receipt[]> {
    return this.http.get<Receipt[]>(this.GET_ALL_FUEL_URL);
  }
}
