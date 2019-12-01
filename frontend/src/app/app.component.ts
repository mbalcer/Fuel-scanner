import {Component} from '@angular/core';
import {Graphic} from "./model/graphic";
import {GraphicService} from "./service/graphic.service";
import {FuelSumService} from "./service/fuel-sum.service";
import {FuelSum} from "./model/fuel-sum";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  graphic: Graphic;
  fuelSum: FuelSum;

  constructor(private graphicService: GraphicService, private fuelService: FuelSumService) {
    this.graphic = {
      url: '',
      content: ''
    };
    this.fuelSum = {
      litres: null,
      cost: null
    }
  }

  sendGraphicUrl() {
    this.graphicService.saveRoom(this.graphic).subscribe(n => {
      this.graphic = n;
      this.readFuelSum();
    });
  }

  readFuelSum() {
    this.fuelService.getLastFuel().subscribe(n => {
      this.fuelSum = n;
    });
  }
}
