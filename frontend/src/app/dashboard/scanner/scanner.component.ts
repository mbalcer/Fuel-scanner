import {Component, OnInit} from '@angular/core';
import {Graphic} from "../../model/graphic";
import {FuelSum} from "../../model/fuel-sum";
import {GraphicService} from "../../service/graphic.service";
import {FuelSumService} from "../../service/fuel-sum.service";
import {FileUploadService} from "../../service/file-upload.service";

@Component({
  selector: 'app-scanner',
  templateUrl: './scanner.component.html',
  styleUrls: ['./scanner.component.css']
})
export class ScannerComponent implements OnInit {

  graphic: Graphic;
  fuelSum: FuelSum;
  fileToUpload: File;

  constructor(private graphicService: GraphicService, private fuelService: FuelSumService, private fileService: FileUploadService) {
    this.graphic = {
      url: '',
      content: ''
    };
    this.fuelSum = {
      litres: null,
      pricePerLitres: null,
      cost: null
    };
    this.fileToUpload = null;
  }

  ngOnInit() {
  }

  scanReceipt() {
    if (this.graphic.url != '') {
      this.graphicService.saveRoom(this.graphic).subscribe(n => {
        this.graphic.url = '';
        this.readFuelSum();
      });
    } else if (this.fileToUpload != null) {
        this.fileService.uploadFile(this.fileToUpload).subscribe(data => {
          console.log(data);
          this.readFuelSum();
          this.fileToUpload = null;
        });
    } else {
      console.log("error");
    }
  }

  readFuelSum() {
    this.fuelService.getLastFuel().subscribe(n => {
      this.fuelSum = n;
    });
  }

  onFileSelected(files : FileList) {
    this.fileToUpload = files.item(0);
  }

}
