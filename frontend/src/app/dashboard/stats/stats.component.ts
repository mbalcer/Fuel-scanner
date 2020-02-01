import {Component, OnInit} from '@angular/core';
import {StatsService} from "../../service/stats.service";
import {ReceiptStats} from "../../model/receipt-stats";
import {CounterStats} from "../../model/counter-stats";

@Component({
  selector: 'app-stats',
  templateUrl: './stats.component.html',
  styleUrls: ['./stats.component.css']
})
export class StatsComponent implements OnInit {

  user = "aniaNowicka"; //TODO change user
  receiptStats: ReceiptStats[] = [];
  counterStats: CounterStats[] = [];
  statsType: StatsType[] = [
      {value: "tank", viewValue: "Suma tankowania"},
      {value: "fuel", viewValue: "Koszty paliwa"}
  ];
  selectedStatsType: StatsType = {
      value: '',
      viewValue: ''
  };

  title = 'Wykres';
  type = 'LineChart';
  dataChart = [];
  columnNames = [];

  constructor(private statsService: StatsService) {
    this.getAllStatsByUser();
  }

  ngOnInit() {
  }

  private getAllStatsByUser() {
    this.statsService.getAllReceiptStats(this.user).subscribe(n => {
      this.receiptStats = n;
    });
    this.statsService.getAllCounterStats(this.user).subscribe(n => {
      this.counterStats = n;
    });
  }

  private prepareData(data) {
    this.dataChart = [];
    data.forEach(i => {
        if (this.selectedStatsType.value == 'tank') {
            this.dataChart.push([i.yearMonth, i.cost, i.litres]);
        } else if (this.selectedStatsType.value == 'fuel') {
            this.dataChart.push([i.yearMonth, i.minCostPerLitre, i.averageCostPerLitre, i.maxCostPerLitre])
        }
    });
  }

  changeStats(statsType) {
    this.selectedStatsType = statsType;
    if (this.selectedStatsType.value == 'tank') {
        this.columnNames = ['Data', 'Koszt paliwa', "Zatankowane paliwo"];
    } else if (this.selectedStatsType.value == 'fuel') {
        this.columnNames = ['Data', 'Minimalna cena paliwa', "Srednia cena paliwa", "Maksymalna cena paliwa"];
    }
      this.prepareData(this.receiptStats);
  }
}

export interface StatsType {
    value: string,
    viewValue: string
}

