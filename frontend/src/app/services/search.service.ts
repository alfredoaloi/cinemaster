import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {AddEvent} from '../model/AddEvent';
import {Observable} from 'rxjs';
import {Show} from '../model/Show';

@Injectable({
  providedIn: 'root'
})
export class SearchService {

  constructor(private httpClient: HttpClient) { }

  searchShowByName(showName: string): Observable<object> {
    const params = new HttpParams().set('name', showName);
    return this.httpClient.get('http://localhost:8080/shows/search', {withCredentials: true, params});
  }

  getAllCategories(): Observable<object> {
    return this.httpClient.get('http://localhost:8080/shows/categories', {withCredentials: true});
  }


  getHighlightedShows(): Observable<object> {
    const params = new HttpParams().set('highlighted', 'true');
    return this.httpClient.get('http://localhost:8080/shows/search', {withCredentials: true, params});
  }


  getEvents(): Observable<AddEvent[]> {
    return this.httpClient.get<AddEvent[]>('http://localhost:8080/admin/events', { withCredentials: true });
  }

  getShowsWithEvents(): Observable<Show[]> {
    return this.httpClient.get<Show[]>('http://localhost:8080/shows/next-week', { withCredentials: true });
  }

  searchShowByCategory(catName: string): Observable<object> {
    const params = new HttpParams().set('category', catName);
    return this.httpClient.get('http://localhost:8080/shows/search', {withCredentials: true, params});
  }

  getComgingSoonShows(): Observable<Show[]> {
    return this.httpClient.get<Show[]>('http://localhost:8080/shows/search?coming-soon=true', { withCredentials: true });
  }
}
