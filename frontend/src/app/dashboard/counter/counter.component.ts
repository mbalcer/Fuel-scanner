import {Component, OnInit} from '@angular/core';
import {Counter} from "../../model/counter";
import {CounterService} from "../../service/counter.service";
import {User} from "../../model/user";

@Component({
  selector: 'app-counter',
  templateUrl: './counter.component.html',
  styleUrls: ['./counter.component.css']
})
export class CounterComponent implements OnInit {
  displayedColumns: string[] = ['counter', 'date', 'fuel'];
  dataSource = EXAMPLE_DATA;

  user: User = {
    login: 'janKowalski',
    name: 'Jan Kowalski',
    password: 'Qwerty123',
    email: 'janKowalski@gmail.com'
  };

  saveCounter: Counter;

  constructor(private counterService: CounterService) {
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

  save() {
    this.saveCounter.counterLocalDate = Date.parse(this.saveCounter.counterLocalDate) + "";
    this.counterService.saveCounter(this.saveCounter).subscribe(n => {
      this.cleanSaveCounter();
    });
  }

}

export interface StateCounter {
  counter: number,
  fuel: number,
  date: string
}

const EXAMPLE_DATA: StateCounter[] = [
  {counter: 100000, fuel: 5, date: "01/12/2019"},
  {counter: 101032, fuel: 15.5, date: "11/12/2019"},
  {counter: 102333, fuel: 30, date: "27/12/2019"},
  {counter: 102875, fuel: 10.23, date: "03/01/2020"}
];
