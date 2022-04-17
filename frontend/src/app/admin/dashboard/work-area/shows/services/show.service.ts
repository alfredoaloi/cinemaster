import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {Show} from '../../../../../model/Show';
import {HttpClient} from '@angular/common/http';

const SERVICE_URI = 'http://localhost:8080/admin/';

@Injectable({
  providedIn: 'root'
})
export class ShowService {
  private options = { withCredentials: true };
  constructor(private http: HttpClient) { }

  public getShows(): Observable<Show[]>{
    return this.http.get<Show[]>(SERVICE_URI + 'shows', this.options);
  }


  public updateShow(show: object): Observable<any> {
    return this.http.put<object>(SERVICE_URI + 'shows', show, this.options);
  }

  public deleteShow(id: string): Observable<any>{
    return this.http.request('delete', SERVICE_URI + 'shows', { body: {id}, withCredentials: true, responseType: 'text' });
  }

  public createNewShow(show: Show): Observable<Show> {

    // TODO: To implement in the next Sprint
    /* const formData = new FormData();
    formData.append('show', JSON.stringify(show));
    formData.append('showCover', show.coverImageRawData, 'cover' + show.coverImageExtension); */

    return this.http.post<Show>('http://localhost:8080/admin/shows', show, this.options);
  }
}
