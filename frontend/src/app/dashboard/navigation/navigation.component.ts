import {Component, OnInit} from '@angular/core';
import {MatIconRegistry} from "@angular/material";
import {DomSanitizer} from "@angular/platform-browser";
import { AuthenticationService } from '../../service/authentication.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnInit {

  user = "Mateusz";

  constructor(private router: Router, private service: AuthenticationService,
            private iconRegistry: MatIconRegistry, sanitizer: DomSanitizer) {
    iconRegistry.addSvgIcon('scan', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/scan.svg'));
    iconRegistry.addSvgIcon('history', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/history.svg'));
    iconRegistry.addSvgIcon('counter', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/counter.svg'));
    iconRegistry.addSvgIcon('stats', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/stats.svg'));
    iconRegistry.addSvgIcon('info', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/info.svg'));
    iconRegistry.addSvgIcon('signout', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/signout.svg'));
    iconRegistry.addSvgIcon('user', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/user.svg'));
    iconRegistry.addSvgIcon('home', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/home.svg'));
  }

  ngOnInit() {
  }

  logout(){
      this.service.logOut();
      this.router.navigate(['login']);
  }
}
