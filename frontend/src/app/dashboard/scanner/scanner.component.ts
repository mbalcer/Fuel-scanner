import {Component, OnInit} from '@angular/core';
import {Graphic} from "../../model/graphic";
import {FuelSum} from "../../model/fuel-sum";
import {GraphicService} from "../../service/graphic.service";
import {FuelSumService} from "../../service/fuel-sum.service";

@Component({
  selector: 'app-scanner',
  templateUrl: './scanner.component.html',
  styleUrls: ['./scanner.component.css']
})
export class ScannerComponent implements OnInit {

  graphic: Graphic;
  fuelSum: FuelSum;

  constructor(private graphicService: GraphicService, private fuelService: FuelSumService) {
    this.graphic = {
      url: '',
      content: ''
    };
    this.fuelSum = {
      litres: null,
      pricePerLitres: null,
      cost: null
    }
  }

  ngOnInit() {
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

  onFileSelected(event) {
    console.log(event);
  }

}
