import { AfterViewChecked, Component, ElementRef, OnInit } from '@angular/core';
import { User } from '../model/User';
import { FormControl } from '@angular/forms';
import { Observable, of } from 'rxjs';
import { ActivatedRoute } from '@angular/router';
import { UserService } from '../service/user.service';
import { ChatMessage } from '../model/ChatMessage';
import { ChatService } from '../service/chat.service';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import { GlobalService } from '../service/global.service';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.scss']
})
export class ChatComponent implements OnInit, AfterViewChecked {

  profile_img = '/assets/profile.jpg';
  otherUser?: User = JSON.parse(window.sessionStorage.getItem("other_user")!)
  thisUser: User = JSON.parse(window.sessionStorage.getItem('user')!);
  channelName?: string = window.sessionStorage.getItem('channel_name')!;
  socket?: WebSocket;
  stompClient?: Stomp.Client;
  newMessage = new FormControl('');
  messages?: Observable<Array<ChatMessage>>;

  constructor(
    private route: ActivatedRoute,
    private userService: UserService,
    private chatService: ChatService,
    private gs: GlobalService,
    private el: ElementRef) { }


  ngOnInit(): void {
    this.connectToChat();
    console.log(this.el)
    this.el.nativeElement.querySelector("#chat").scrollIntoView();
  }

  ngAfterViewChecked(): void {
    this.scrollDown();
  }

  scrollDown() {
    var container = this.el.nativeElement.querySelector("#chat");
    container.scrollTop = container.scrollHeight;
  }

  connectToChat() {
    this.loadChat();
    console.log('connecting to chat...');
    this.socket = new SockJS(`${this.gs.BASE_API_URL}/ws`);
    this.stompClient = Stomp.over(this.socket);

    this.stompClient.connect({}, (frame) => {
      //func = what to do when connection is established
      console.log('connected to: ' + frame);
      this.stompClient!.subscribe(
        '/topic/messages/' + this.channelName,
        (response) => {
          //func = what to do when client receives data (messages)
          console.log('Fuck==='+response)
          this.loadChat();
        }
      );
    });
  }

  sendMsg() {
    if (this.newMessage.value !== '') {
      this.stompClient!.send(
        '/app/ws/' + this.channelName,
        {},
        JSON.stringify({
          sender: this.thisUser.userName,
          receiver: this.otherUser?.userName,
          content: this.newMessage.value,
        })
      );
      this.newMessage.setValue('');
    }
  }

  loadChat() {
    this.chatService.getMessagesByChatName(this.channelName!).subscribe(data => {
      let mgs: Array<ChatMessage> = data;
      mgs.sort((a, b) => (a.id > b.id) ? 1 : -1)
      this.messages = of(mgs);
    })
    console.log(this.messages);
  }

  whenWasItPublished(date: string) {
    return date;
  }

  logout() {
    window.sessionStorage.clear();
  }

}
