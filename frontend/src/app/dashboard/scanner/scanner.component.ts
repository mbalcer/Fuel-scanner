import {Component, OnInit} from '@angular/core';

import {Receipt} from "../../model/receipt";
import {OcrService} from "../../service/ocr.service";
import {ReceiptService} from "../../service/receipt.service";

@Component({
  selector: 'app-scanner',
  templateUrl: './scanner.component.html',
  styleUrls: ['./scanner.component.css']
})
export class ScannerComponent implements OnInit {

  receipt: Receipt;
  fileToUpload: File;
  url: string;
  progress: boolean;

  constructor(private ocrService: OcrService, private receiptService: ReceiptService) {
    this.clearReceipt();
    this.fileToUpload = null;
    this.url = '';
    this.progress = false;
  }

  ngOnInit() {
  }

  clearReceipt() {
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
  }

  scanReceipt() {
    this.progress = true;
    if (this.url != '') {
      this.ocrService.scanReceipt(this.url).subscribe(data => {
        this.receipt = data;
        this.url = '';
        this.progress = false;
      });
    } else if (this.fileToUpload != null) {
        this.ocrService.uploadFile(this.fileToUpload).subscribe(data => {
          this.receipt = data;
          this.fileToUpload = null;
          this.progress = false;
        });
    } else {
      console.log("error");
    }
  }

  saveReceipt() {
      this.receiptService.saveReceipt(this.receipt).subscribe(n => {
         this.clearReceipt();
      });
  }

  onFileSelected(files : FileList) {
    this.fileToUpload = files.item(0);
  }

}
