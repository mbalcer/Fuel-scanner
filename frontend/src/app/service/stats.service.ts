import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders } from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";
import {ReceiptStats} from "../model/receipt-stats";
import {CounterStats} from "../model/counter-stats";

@Injectable({
  providedIn: 'root'
})
export class StatsService {
  private STATS_URL = environment.mainUrl + "/api/stats";
  private GET_ALL_RECEIPT_STATS_URL = `${this.STATS_URL}/receiptStats/`;
  private GET_ALL_COUNTER_STATS_URL = `${this.STATS_URL}/counterStats/`

  constructor(private http: HttpClient) { }

  getAllReceiptStats(login: string) : Observable<ReceiptStats[]> {
    let admin = 'admin';
    const headers = new HttpHeaders({ Authorization: 'Basic ' + btoa(admin + ':' + admin) });
    return this.http.get<ReceiptStats[]>(this.GET_ALL_RECEIPT_STATS_URL + login, {headers});
  }

  getAllCounterStats(login: string) : Observable<CounterStats[]> {
    let admin = 'admin';
    const headers = new HttpHeaders({ Authorization: 'Basic ' + btoa(admin + ':' + admin) });
    return this.http.get<CounterStats[]>(this.GET_ALL_COUNTER_STATS_URL + login, {headers});
  }
}
