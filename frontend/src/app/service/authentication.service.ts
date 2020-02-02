import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { map } from 'rxjs/operators';

export class User{
  constructor(
    public status:string,
     ) {}
}

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  username: string;
  user: User;

  constructor( private httpClient:HttpClient) { }

     authenticate(login: string, password: string) {
      let admin = 'admin'
      const headers = new HttpHeaders({ Authorization: 'Basic ' + btoa(admin + ':' + admin) });

      return this.httpClient.get<User>("http://localhost:8080/api/user/validate/" + login + "/" + password, {headers}).pipe(
       map(
         data => {
          sessionStorage.setItem('username', login);
          return data;
         })
       )}

  isUserLoggedIn() {
    let user = sessionStorage.getItem('username')

    if(user === null){
     console.log(false)
    }
    return !(user === null)
  }

  logOut() {
    sessionStorage.removeItem('username')
  }

  getLogin(){
    let login = sessionStorage.getItem('username');
    return login;
  }

  getUsername(){
    this.findUsername();
    return this.username;
  }

  findUsername(){
    let login = this.getLogin()
    let admin = 'admin'
    const headers = new HttpHeaders({ Authorization: 'Basic ' + btoa(admin + ':' + admin) });

    return this.httpClient.get<string>("http://localhost:8080/api/user/name/" + login, {headers}).pipe(
      map(
        username => {
            this.username = username;
        })
    )}

  getUser(){
    this.findUser();
    return this.user;
  }

  findUser(){
    let login = this.getLogin()
    let admin = 'admin'
    const headers = new HttpHeaders({ Authorization: 'Basic ' + btoa(admin + ':' + admin) });

    return this.httpClient.get<User>("http://localhost:8080/api/user/" + login, {headers}).pipe(
      map( user => {
         this.user = user;
         } )
    )}
}