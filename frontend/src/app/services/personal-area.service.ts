import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PersonalAreaService {

  constructor(private httpClient: HttpClient) { }

  getBookings(): Observable<any>{
    return this.httpClient.get('http://localhost:8080/user/tickets', {withCredentials: true});
  }

  deleteBooking(id): Observable<any>{
    return this.httpClient.post( 'http://localhost:8080/booking/remove',  [{id}], {withCredentials: true, responseType: 'text' });
  }
  getPersonalData(): Observable<any>{
    return this.httpClient.get('http://localhost:8080/user/profile', {withCredentials: true});
  }
  savePersonalData(personalData): Observable<any>{
    return this.httpClient.post( 'http://localhost:8080/user/update',  personalData, {withCredentials: true, responseType: 'text' });
  }
  savePassword(personalData): Observable<any>{
    return this.httpClient.post( 'http://localhost:8080/user/change-password',  personalData,
      {withCredentials: true, responseType: 'text' });
  }

}
