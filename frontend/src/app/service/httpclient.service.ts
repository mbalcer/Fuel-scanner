import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

export class User{}

@Injectable({
  providedIn: 'root'
})

export class HttpClientService {

  constructor(
    private httpClient:HttpClient
  ) {
     }

  public createEmployee(user: User) {
    let admin='admin'

    const headers = new HttpHeaders({ Authorization: 'Basic ' + btoa(admin + ':' + admin) });
    return this.httpClient.post<User>("http://localhost:8080/api/user", user, {headers});
  }
}