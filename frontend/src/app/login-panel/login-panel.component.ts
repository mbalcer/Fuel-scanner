import {Component, OnInit} from '@angular/core';
import { AuthenticationService } from '../service/authentication.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login-panel',
  templateUrl: './login-panel.component.html',
  styleUrls: ['./login-panel.component.css']
})

export class LoginPanelComponent implements OnInit {

  login = ''
  password = ''

  invalidLogin = false

  constructor(private router: Router,
    private loginservice: AuthenticationService) { }

  ngOnInit() {
  }

  checkLogin() {
    (this.loginservice.authenticate(this.login, this.password).subscribe(
      data => {
        this.router.navigate(['dashboard'])
        this.invalidLogin = false
      },
      error => {
        this.invalidLogin = true
      }
    )
    );
  }
}