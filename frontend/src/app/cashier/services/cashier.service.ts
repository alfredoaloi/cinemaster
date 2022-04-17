import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

const SERVICE_URI = 'http://localhost:8080/cashier/';
const SHOW_PATH = 'http://localhost:8080/shows/';
@Injectable({
  providedIn: 'root'
})
export class CashierService {

  constructor(private http: HttpClient) { }

  public getEvents(): Observable<any> {
    return this.http.get(SERVICE_URI + 'today-events', {withCredentials: true});
  }

  public getPurchases(): Observable<any> {
    return this.http.get(SERVICE_URI + 'bookings', { withCredentials: true });
  }

  public deletePurchase(id: string): Observable<any> {
    return this.http.post('http://localhost:8080/booking/remove', [{id}], {withCredentials: true, responseType: 'text'});
  }

  public getBookedSeats(id: string): Observable<any>{
    return this.http.get<any>(SHOW_PATH + 'events/seats/booked?id=' + id);
  }

  public bookEventsSeats(book: any): Observable<any> {
    return this.http.post<any>('http://localhost:8080/booking/select', book, { withCredentials: true });
  }

  public paymentCompletedNotification(booking: any): Observable<any> {
    return this.http.post('http://localhost:8080/booking/confirm', booking, {withCredentials: true, responseType: 'text'});
  }

}
