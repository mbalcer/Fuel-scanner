import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-counter',
  templateUrl: './counter.component.html',
  styleUrls: ['./counter.component.css']
})
export class CounterComponent implements OnInit {
  displayedColumns: string[] = ['counter', 'date'];
  dataSource = EXAMPLE_DATA;

  constructor() { }

  ngOnInit() {
  }

}

export interface StateCounter {
  counter: number;
  date: string;
}

const EXAMPLE_DATA: StateCounter[] = [
  {counter: 100000, date: "01/12/2019"},
  {counter: 101032, date: "11/12/2019"},
  {counter: 102333, date: "27/12/2019"},
  {counter: 102875, date: "03/01/2020"}
];
