import { AfterViewChecked, Component, ElementRef, OnInit } from '@angular/core';
import { User } from '../model/User';
import { FormControl } from '@angular/forms';
import { Observable, of } from 'rxjs';
import { ChatMessage } from '../model/ChatMessage';
import { ChatService } from '../service/chat.service';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import { GlobalService } from '../service/global.service';

@Component({
  selector: 'app-chats',
  templateUrl: './chats.component.html',
  styleUrls: ['./chats.component.scss']
})
export class ChatsComponent implements OnInit, AfterViewChecked {

  profile_img = '/assets/profile.jpg';
  otherUser: User = this.gs.userIamChattingWith();
  haveOtherUser: boolean = this.otherUser ? true : false;
  chatTitle: string = this.haveOtherUser ? `${this.otherUser?.name}` : 'Select user to with';
  thisUser: User = this.gs.currentUser();
  channelName: string = this.gs.currentChatChannel();
  socket?: WebSocket;
  stompClient?: Stomp.Client;
  newMessage = new FormControl('');
  messages?: Observable<Array<ChatMessage>>;
  public loader: number = 0;

  constructor(
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
      this.stompClient!.subscribe(
        '/topic/messages/' + this.channelName,
        (response) => {
          //func = what to do when client receives data (messages)
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
    if (this.channelName) {
      this.chatService.getMessagesByChatName(this.channelName!).subscribe(data => {
        let mgs: Array<ChatMessage> = data;
        mgs.sort((a, b) => (a.id > b.id) ? 1 : -1)
        this.messages = of(mgs);
      })
      console.log(this.messages);
    }
  }

  whenWasItPublished(date: string) {
    let d = new Date(date);
    return '';
  }

  reload(): void {
    this.loader++;
  }

}
