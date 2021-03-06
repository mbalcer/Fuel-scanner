import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {Receipt} from "../model/receipt";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class OcrService {
  private OCR_URL = environment.mainUrl+"/api/ocr";
  private UPLOAD_FILE_URL = `${this.OCR_URL}/file/`;
  private POST_SCAN_RECEIPT_URL = `${this.OCR_URL}/url/`;

  constructor(private http: HttpClient) { }

  uploadFile(file: File, turned: boolean): Observable<Receipt> {
    const formdata: FormData = new FormData();
    formdata.append('file', file);
    // const req = new HttpRequest('POST', this.UPLOAD_FILE_URL, formdata, {
    //   reportProgress: true,
    //   responseType: 'text'
    // });

    let admin = 'admin';
    const headers = new HttpHeaders({ Authorization: 'Basic ' + btoa(admin + ':' + admin) });
    return this.http.post<Receipt>(this.UPLOAD_FILE_URL+turned, formdata, {headers});
  }

  scanReceipt(url: string, turned: boolean): Observable<Receipt> {
    let admin = 'admin';
    const headers = new HttpHeaders({ Authorization: 'Basic ' + btoa(admin + ':' + admin) });
    return this.http.post<Receipt>(this.POST_SCAN_RECEIPT_URL + turned, url, {headers});
  }
}
