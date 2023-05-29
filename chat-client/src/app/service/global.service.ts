import { HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { User } from '../model/User';

@Injectable({ providedIn: 'root' })
export class GlobalService {
  BASE_API_URL: string = "http://localhost:8080/api/v1";
  HTTP_OPTIONS = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };
  constructor() { }


  public handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error); 
      return of(result as T);
    };
  }

  public currentUser(): User {
    let storedUser = window.sessionStorage.getItem('user');
    return storedUser ? JSON.parse(storedUser) : null;
  }

  public userIamChattingWith(): User {
    let storedOtherUser = window.sessionStorage.getItem('other_user');
    return storedOtherUser ? JSON.parse(storedOtherUser) : null;
  }

  public currentChatChannel(): string {
    return window.sessionStorage.getItem('channel_name')!;
  }

 
}