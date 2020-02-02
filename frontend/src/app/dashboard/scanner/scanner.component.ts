import {Component, OnInit} from '@angular/core';

import {Receipt} from "../../model/receipt";
import {OcrService} from "../../service/ocr.service";
import {ReceiptService} from "../../service/receipt.service";
import {AuthenticationService} from "../../service/authentication.service";
import {User} from "../../model/user";

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
  turned: boolean;
  user: User = {
    login: '',
    name: '',
    password: '',
    email: ''
  };

  constructor(private ocrService: OcrService, private receiptService: ReceiptService, private userService: AuthenticationService) {
    this.userService.getUser().subscribe(n => {
       this.user = n;
    });
    this.clearReceipt();
    this.fileToUpload = null;
    this.url = '';
    this.progress = false;
    this.turned = false;
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
      this.ocrService.scanReceipt(this.url, this.turned).subscribe(data => {
        this.receipt = data;
        this.url = '';
        this.progress = false;
      });
    } else if (this.fileToUpload != null) {
        this.ocrService.uploadFile(this.fileToUpload, this.turned).subscribe(data => {
          this.receipt = data;
          this.fileToUpload = null;
          this.progress = false;
        });
    } else {
      console.log("error");
    }
  }

  saveReceipt() {
      this.receipt.user = this.user;
      this.receiptService.saveReceipt(this.receipt).subscribe(n => {
         this.clearReceipt();
      });
  }

  onFileSelected(files : FileList) {
    this.fileToUpload = files.item(0);
  }

}
