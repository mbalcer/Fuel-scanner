import {Component, OnInit} from '@angular/core';
import {Counter} from "../../model/counter";
import {CounterService} from "../../service/counter.service";
import {User} from "../../model/user";
import {MatTableDataSource} from "@angular/material";
import {AuthenticationService} from '../../service/authentication.service';

@Component({
  selector: 'app-counter',
  templateUrl: './counter.component.html',
  styleUrls: ['./counter.component.css']
})
export class CounterComponent implements OnInit {
  displayedColumns: string[] = ['counter', 'date', 'fuel'];
  dataSource = new MatTableDataSource<Counter>();
  counterList: Counter[];

  user: User = {
      login: '',
      name: '',
      password: '',
      email: ''
  };

  saveCounter: Counter;

  constructor(private counterService: CounterService,
                      private service: AuthenticationService) {
    this.getUser();
    this.cleanSaveCounter();
  }

  ngOnInit() {
  }

  cleanSaveCounter() {
    this.saveCounter = {
      counterState: null,
      counterLocalDate: '',
      fuelTank: null,
      user: this.user
    }
  }

  refreshTable() {
      this.dataSource = new MatTableDataSource<Counter>(this.counterList);
  }

  save() {
    this.saveCounter.counterLocalDate = Date.parse(this.saveCounter.counterLocalDate) + "";
    this.counterService.saveCounter(this.saveCounter).subscribe(n => {
        this.counterList.push(n);
        this.cleanSaveCounter();
        this.refreshTable();
    });
  }

  getCounters() {
    this.counterService.getAllCounterByUser(this.user.login).subscribe(n => {
        this.counterList = n;
        this.refreshTable();
    });
  }

  getUser(){
    this.service.getUser().subscribe(data => {
        this.user = data;
        this.getCounters();
    });
  }
}
