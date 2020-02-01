import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";
import {ReceiptStats} from "../model/receipt-stats";

@Injectable({
  providedIn: 'root'
})
export class StatsService {
  private STATS_URL = environment.mainUrl + "/api/stats";
  private GET_ALL_STATS_URL = `${this.STATS_URL}/receiptStats/`;

  constructor(private http: HttpClient) { }

  getAllStats(login: string) : Observable<ReceiptStats[]> {
    return this.http.get<ReceiptStats[]>(this.GET_ALL_STATS_URL + login);
  }
}
