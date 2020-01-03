import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.css']
})
export class HistoryComponent implements OnInit {
  dataSource = EXAMPLE_DATA;

  constructor() { }

  ngOnInit() {
  }

}

export interface ReceiptScan {
  date: string,
  litres: number,
  costPerLitre: number,
  costForAll: number
}

const EXAMPLE_DATA: ReceiptScan[] = [
  {date: "03/12/2019", litres: 7.65, costPerLitre: 5.02, costForAll: 38.40},
  {date: "11/12/2019", litres: 10.0, costPerLitre: 5.08, costForAll: 50.80},
  {date: "21/12/2019", litres: 8.24, costPerLitre: 5.00, costForAll: 41.20},
  {date: "28/12/2019", litres: 16.21, costPerLitre: 5.05, costForAll: 81.86}
];
