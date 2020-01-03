import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-scanner',
  templateUrl: './scanner.component.html',
  styleUrls: ['./scanner.component.css']
})
export class ScannerComponent implements OnInit {

  recognizedText = {
    date: '2020-01-20',
    litres: 7.65,
    costPerLitre: 5.02,
    costForAll: 38.40
  };

  constructor() { }

  ngOnInit() {
  }

  onFileSelected(event) {
    console.log(event);
  }

}
