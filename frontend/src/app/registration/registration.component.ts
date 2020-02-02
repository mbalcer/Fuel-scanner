import {Component, OnInit} from '@angular/core';
import { HttpClientService } from '../service/httpclient.service';
import { Router } from '@angular/router';

export class User{
  constructor(
    public login:string,
    public name:string,
    public password:string,
    public email:string,
  ) {}
}

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['../login-panel/login-panel.component.css',
              './registration.component.css']
})
export class RegistrationComponent implements OnInit {

  login = ''
  name = ''
  password = ''
  email = ''

  invalidLogin = false

  constructor(private router: Router,
    private httpclientservice: HttpClientService) { }

  ngOnInit() {
  }

  register() {
    let user: User = {
          login: this.login,
          name: this.name,
          password: this.password,
          email: this.email
        };

    (this.httpclientservice.createEmployee(user).subscribe(
      data => {
        console.log(true)
        this.router.navigate(['login'])
        this.invalidLogin = false
      },
      error => {
        this.invalidLogin = true
      }
    )
    );
  }
}