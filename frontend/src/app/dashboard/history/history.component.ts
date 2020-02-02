import {Component, OnInit} from '@angular/core';
import {Receipt} from "../../model/receipt";
import {ReceiptService} from "../../service/receipt.service";
import {AuthenticationService} from "../../service/authentication.service";
import {User} from "../../model/user";

@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.css']
})
export class HistoryComponent implements OnInit {
  dataSource : Receipt[] = [];
  user: User = {
    login: '',
    name: '',
    password: '',
    email: ''
  };
  constructor(private fuelSumService: ReceiptService, private userService: AuthenticationService) {
    userService.getUser().subscribe(n => {
        this.user = n;
        this.getHistory();
    });

  }

  ngOnInit() {
  }

  getHistory() {
    this.fuelSumService.getAllFuel(this.user.login).subscribe(n => {
      this.dataSource = n;
    });
  }

}
