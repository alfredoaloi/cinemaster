import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  constructor(private httpClient: HttpClient) { }

  authenticateUser(loginData): any {
    // TODO concordare URL e dati restituiti da REST
    return this.httpClient.post<string>('http://localhost:8080/login', {username: loginData.username,
      password: loginData.password}, { withCredentials: true });
  }

  authenticateUserBis(user, pass): any {
    // TODO concordare URL e dati restituiti da REST
    return this.httpClient.post<string>('http://localhost:8080/login', {username: user,
      password: pass}, { withCredentials: true });
  }

  registrationUser(registrationData): any{
    return this.httpClient.post<string>('http://localhost:8080/registration',
      {username: registrationData.username, firstName: registrationData.firstname, hashedPassword: registrationData.password,
        // tslint:disable-next-line:max-line-length
      lastName: registrationData.lastname, email: registrationData.email, birthdate: registrationData.birthdate, gender: registrationData.gender}, { withCredentials: true });
  }

  logoutUser(): any {
    return this.httpClient.request('post', 'http://localhost:8080/logout', {body: {}, withCredentials: true, responseType: 'text' });
  }

}
