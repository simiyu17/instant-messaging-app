import { Component, OnInit } from '@angular/core';
import { UserService } from '../service/user.service';
import { ChatService } from '../service/chat.service';
import { Router } from '@angular/router';
import { User } from '../model/User';
import { map } from 'rxjs';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss']
})
export class MainComponent implements OnInit{

  public alluser: any = [];
  check = window.sessionStorage.getItem('user');
  currentUser: any = (JSON.parse(this.check!)).name;
  chatId: any = 0;
  public chatData: any = [];

  constructor(private router: Router, private userService: UserService, private chatService: ChatService) { }

  ngOnInit(): void {
    let all = setInterval(() =>{
      this.userService.getAllUsers().subscribe({
        next: (data) => {
        this.alluser = data;
      }})
    }, 10000)
      
  }


  goToChat(otherChatUser: User) {
    this.chatService.createChatForTwoUsers(JSON.parse(window.sessionStorage.getItem("user")!), otherChatUser).subscribe({
      next: (data) => {
        this.chatId = data.id;
        window.sessionStorage.setItem("chat_id", this.chatId);
        window.sessionStorage.setItem("other_user", JSON.stringify(otherChatUser));
        window.sessionStorage.setItem("channel_name", `${data.chatName}`);
        window.sessionStorage.setItem("gotochat", "false");
        this.router.navigateByUrl('/chats');
      },
      error: (error) => {
        console.log(error)
         alert('Error occured')
      
      }
    });

  }
}
