import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

const SERVICE_URI = 'http://localhost:8080/admin/';
@Injectable({
  providedIn: 'root'
})
export class SimpleTextService {

  constructor(private http: HttpClient) { }

  public addActor(actor: object): Observable<any> {
    return this.http.post(SERVICE_URI + 'actors', actor, {withCredentials: true});
  }

  public addDirector(director: object): Observable<any> {
    return this.http.post(SERVICE_URI + 'directors', director, {withCredentials: true});
  }

  public addGenre(genre: object): Observable<any> {
    return this.http.post(SERVICE_URI + 'categories', genre, {withCredentials: true});
  }

  public addRoom(room: object): Observable<any> {
    return this.http.post(SERVICE_URI + 'rooms', room, {withCredentials: true});
  }
}
