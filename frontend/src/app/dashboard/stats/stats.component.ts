import {Component, OnInit} from '@angular/core';
import {StatsService} from "../../service/stats.service";
import {ReceiptStats} from "../../model/receipt-stats";

@Component({
  selector: 'app-stats',
  templateUrl: './stats.component.html',
  styleUrls: ['./stats.component.css']
})
export class StatsComponent implements OnInit {

  user = "aniaNowicka"; //TODO change user
  receiptStats: ReceiptStats[];
  statsType: StatsType[] = [
      {value: "tank", viewValue: "Suma tankowania"},
      {value: "fuel", viewValue: "Koszty paliwa"}
  ];
  selectedStatsType: string;

  title = 'Wykres';
  type = 'LineChart';
  dataChart = [];
  columnNames = ['Data', 'Koszt paliwa', "Zatankowane paliwo"];

  constructor(private statsService: StatsService) {
    this.getAllStatsByUser();
  }

  ngOnInit() {
  }

  private getAllStatsByUser() {
    this.statsService.getAllStats(this.user).subscribe(n => {
      this.receiptStats = n;
      this.prepareData(n);
    });
  }

  private prepareData(data) {
    data.forEach(i => {
      this.dataChart.push([i.yearMonth, i.cost, i.litres]);
    });
  }

  changeStats(event) {
    this.selectedStatsType = event;
  }
}

export interface StatsType {
    value: string,
    viewValue: string
}

