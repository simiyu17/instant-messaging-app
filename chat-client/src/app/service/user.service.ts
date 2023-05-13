import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { GlobalService } from './global.service';
import { User } from '../model/User';
import { Observable } from 'rxjs';
import { LoginRequest } from '../model/LoginRequest';

@Injectable({providedIn: 'root'})
export class UserService {
  constructor(private httpClient: HttpClient, private globalService: GlobalService) { }
  
  register(user: LoginRequest): Observable<any> {
    return this.httpClient.post(`${this.globalService.BASE_API_URL}/users`, JSON.stringify(user));
  }

  login(user: LoginRequest): Observable<any> {
    return this.httpClient.post(`${this.globalService.BASE_API_URL}/users/login`, JSON.stringify(user));
  }

  getUserByUsername(userName: string): Observable<any> {
    return this.httpClient.get(`${this.globalService.BASE_API_URL}/users/${userName}`);
  }

  getAllUsers(): Observable<any> {
    return this.httpClient.get(`${this.globalService.BASE_API_URL}/users`);
  }
}