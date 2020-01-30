import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
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
    return this.http.post<Counter>(this.SAVE_COUNTER_URL, counter);
  }

  getAllCounterByUser(login: string): Observable<Counter[]> {
    return this.http.get<Counter[]>(this.GET_COUNTER_BY_USER_URL + login);
  }
}
