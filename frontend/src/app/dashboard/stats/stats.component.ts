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

  constructor(private statsService: StatsService) {
    this.getAllStatsByUser();
  }

  ngOnInit() {
  }

  private getAllStatsByUser() {
    this.statsService.getAllStats(this.user).subscribe(n => {
      this.receiptStats = n;
      console.log(this.receiptStats);
    });
  }
}
