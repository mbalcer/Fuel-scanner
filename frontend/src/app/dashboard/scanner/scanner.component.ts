import {Component, OnInit} from '@angular/core';

import {Receipt} from "../../model/receipt";
import {OcrService} from "../../service/ocr.service";

@Component({
  selector: 'app-scanner',
  templateUrl: './scanner.component.html',
  styleUrls: ['./scanner.component.css']
})
export class ScannerComponent implements OnInit {

  receipt: Receipt;
  fileToUpload: File;
  url: string;

  constructor(private ocrService: OcrService) {
    this.receipt = {
      url: '',
      content: '',
      receiptLocalDate: '',
      litres: null,
      pricePerLitres: null,
      cost: null,
      user: {
        login: '',
        name: '',
        password: '',
        email: ''
      }
    };
    this.fileToUpload = null;
    this.url = '';
  }

  ngOnInit() {
  }

  scanReceipt() {
    if (this.url != '') {
        console.log(this.url);
      this.ocrService.scanReceipt(this.url).subscribe(n => {
        this.receipt = n;
        this.url = '';
      });
    } else if (this.fileToUpload != null) {
        this.ocrService.uploadFile(this.fileToUpload).subscribe(data => {
          console.log(data);
          this.receipt = data;
          this.fileToUpload = null;
        });
    } else {
      console.log("error");
    }
  }

  onFileSelected(files : FileList) {
    this.fileToUpload = files.item(0);
  }

}
