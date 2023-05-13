import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { GlobalService } from './global.service';
import { Observable } from 'rxjs';
import { User } from '../model/User';
import { Chat } from '../model/Chat';

@Injectable({providedIn: 'root'})
export class ChatService {

  constructor(private httpClient: HttpClient, private gs: GlobalService) { }
  
  getMessagesByChatId(chatId: number): Observable<any> {
    return this.httpClient.get(`${this.gs.BASE_API_URL}/chats/${chatId}/messages`);
  }

  getMessagesByChatName(chatname: string): Observable<any> {
    return this.httpClient.get(`${this.gs.BASE_API_URL}/messages/${chatname}`);
  }

  getChatName(userOne: User, userTwo: User): string {
    const id1 = userOne.id!;
    const nick1 = userOne.userName;
    const id2 = userTwo?.id!;
    const nick2 = userTwo?.userName!;

      return (id1 > id2) ? (nick1 + '&' + nick2) : (nick2 + '&' + nick1);
  }

  createOrReturn(chatDto: any): Observable<any> {
    return this.httpClient.post(`${this.gs.BASE_API_URL}/chats`, JSON.stringify(chatDto));
  }

  createChatForTwoUsers(userOne: User, userTwo: User): Observable<any> {
    const chatName = this.getChatName(userOne, userTwo);
    const chatDto: any = {userOne: `${userOne.userName}`, userTwo: `${userTwo.userName}`, chatName: chatName};
    console.log(chatDto)
    return this.httpClient.post(`${this.gs.BASE_API_URL}/chats`, JSON.stringify(chatDto));
  }
}
