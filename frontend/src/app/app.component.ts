import { Component } from '@angular/core';
import {Graphic} from "./model/graphic";
import {GraphicService} from "./service/graphic.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  graphic: Graphic;

  constructor(private graphicService: GraphicService) {
    this.graphic = {
      id: null,
      url: '',
      content: '',
      fuelAmount: null,
      fuelPrice: null,
      receiptValue: null
    };
  }

  sendGraphicUrl() {
    this.graphicService.saveRoom(this.graphic).subscribe(n => {
      this.graphic = n;
    });
  }
}
