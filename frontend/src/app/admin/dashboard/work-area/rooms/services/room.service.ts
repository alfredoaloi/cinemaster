import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {EventRoom} from '../../../../../model/EventRoom';

const SERVICE_URI = 'http://localhost:8080/admin/rooms';

@Injectable({
  providedIn: 'root'
})
export class RoomService {
  private options = { withCredentials: true };
  constructor(private http: HttpClient) { }

  public getRooms(): Observable<EventRoom[]>{
    return this.http.get<EventRoom[]>(SERVICE_URI, this.options);
  }

  public saveRoom(room: object): Observable<any> {
    return this.http.post<any>(SERVICE_URI, room, this.options);
  }

  public deleteRoom(id: number): Observable<any>{
    return this.http.request('delete', SERVICE_URI, { body: {id}, withCredentials: true, responseType: 'text' });
  }

  public modifyRoom(room: object): Observable<any>{
    return this.http.put(SERVICE_URI, room, this.options);
  }
}
