import { Injectable } from '@angular/core';
import {HttpClient, HttpEvent, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs";
import {Receipt} from "../model/receipt";

@Injectable({
  providedIn: 'root'
})
export class OcrService {
  private MAIN_URL = "http://localhost:8080/api/ocr";
  private UPLOAD_FILE_URL = `${this.MAIN_URL}/file`;
  private POST_SCAN_RECEIPT_URL = `${this.MAIN_URL}/url`;

  constructor(private http: HttpClient) { }

  uploadFile(file: File): Observable<Receipt> {
    const formdata: FormData = new FormData();
    formdata.append('file', file);
    // const req = new HttpRequest('POST', this.UPLOAD_FILE_URL, formdata, {
    //   reportProgress: true,
    //   responseType: 'text'
    // });

    return this.http.post<Receipt>(this.UPLOAD_FILE_URL, formdata);
  }

  scanReceipt(url: string): Observable<Receipt> {
    return this.http.post<Receipt>(this.POST_SCAN_RECEIPT_URL, url);
  }
}
