import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Counter} from "../model/counter";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class CounterService {
  private MAIN_URL = "http://localhost:8080/api/counter";
  private SAVE_COUNTER_URL = `${this.MAIN_URL}`;
  private GET_COUNTER_BY_USER_URL = `${this.MAIN_URL}/allByUser/`;

  constructor(private http: HttpClient) { }

  saveCounter(counter: Counter): Observable<Counter> {
    return this.http.post<Counter>(this.SAVE_COUNTER_URL, counter);
  }

  getAllCounterByUser(login: string): Observable<Counter[]> {
    return this.http.get<Counter[]>(this.GET_COUNTER_BY_USER_URL + login);
  }
}
