import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Show} from '../../model/Show';

@Injectable({
  providedIn: 'root'
})
export class BookingEventService {

  constructor(private httpClient: HttpClient) { }

  bookEventsSeats(book: any): Observable<any> {
    return this.httpClient.post<any>('http://localhost:8080/booking/select', book, { withCredentials: true });
  }

  verifiyCouponValidity(coupon: any): Observable<number> {
    return this.httpClient.post<number>('http://localhost:8080/booking/coupon', coupon, {withCredentials: true});
  }

  paymentCompletedNotification(booking: any): Observable<any> {
    return this.httpClient.post('http://localhost:8080/booking/confirm', booking, {withCredentials: true, responseType: 'text'});
  }
}
