import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

const SHOW_PATH = 'http://localhost:8080/shows/';
const EVENT_PATH = 'http://localhost:8080/events/';

@Injectable({
  providedIn: 'root'
})
export class ShowDetailService {

  constructor(private http: HttpClient) { }

  public getShowDetail(id: string): Observable<any>{
    return this.http.get<any>(SHOW_PATH + 'details?id=' + id);
  }

  public getshowEvents(id: string): Observable<any>{
    return this.http.get<any>(SHOW_PATH + 'events?id=' + id);
  }

  public getBookedSeats(id: string): Observable<any>{
    return this.http.get<any>(SHOW_PATH + 'events/seats/booked?id=' + id);
  }
}
