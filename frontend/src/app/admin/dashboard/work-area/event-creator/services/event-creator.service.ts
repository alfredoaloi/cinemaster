import { Injectable } from '@angular/core';
import {AddEvent} from '../../../../../model/AddEvent';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class EventCreatorService {

  constructor(private httpClient: HttpClient) { }

  createNewEvent(event: AddEvent): Observable<any> {
    return this.httpClient.post('http://localhost:8080/admin/events', event, { withCredentials: true, responseType: 'text' });
  }
}
