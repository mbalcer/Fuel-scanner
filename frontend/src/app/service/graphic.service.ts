import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Graphic} from "../model/graphic";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class GraphicService {
  private MAIN_URL = "http://localhost:8080/api/ocr";
  private POST_GRAPHIC_URL = `${this.MAIN_URL}/url`;

  constructor(private http: HttpClient) { }

  saveRoom(graphic: Graphic): Observable<Graphic> {
    return this.http.post<Graphic>(this.POST_GRAPHIC_URL, graphic);
  }
}
