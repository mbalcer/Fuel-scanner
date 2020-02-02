import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders } from "@angular/common/http";
import {Counter} from "../model/counter";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class CounterService {
  private COUNTER_URL = environment.mainUrl+"/api/counter";
  private SAVE_COUNTER_URL = `${this.COUNTER_URL}`;
  private GET_COUNTER_BY_USER_URL = `${this.COUNTER_URL}/allByUser/`;

  constructor(private http: HttpClient) { }

  saveCounter(counter: Counter): Observable<Counter> {
    let admin = 'admin';
    const headers = new HttpHeaders({ Authorization: 'Basic ' + btoa(admin + ':' + admin) });
    return this.http.post<Counter>(this.SAVE_COUNTER_URL, counter, {headers});
  }

  getAllCounterByUser(login: string): Observable<Counter[]> {
    let admin = 'admin';
    const headers = new HttpHeaders({ Authorization: 'Basic ' + btoa(admin + ':' + admin) });
    return this.http.get<Counter[]>(this.GET_COUNTER_BY_USER_URL + login, {headers});
  }
}
