import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { UserService } from '../service/user.service';
import { ChatService } from '../service/chat.service';
import { User } from '../model/User';
import { filter, map } from 'rxjs';
import { GlobalService } from '../service/global.service';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.scss']
})
export class UsersComponent implements OnInit{

  profile_img = '/assets/profile.jpg';
  public alluser: any = [];
  currentUser: any = this.gs.currentUser();
  currentUserName: any = this.currentUser.name;
  chatId: any = 0;
  public chatData: any = [];

  constructor(private userService: UserService, private chatService: ChatService, private gs: GlobalService) { }

  ngOnInit(): void {
    let all = setInterval(() =>{ 
      this.userService.getAllUsers().pipe(
        map(users => users.filter((u: User) => u.userName !== this.currentUser.userName))
      ).subscribe({
        next: (data) => {
        this.alluser = data;
      }})
    }, 3000)
      
  }


  goToChat(otherChatUser: User) {
    this.chatService.createChatForTwoUsers(this.currentUser, otherChatUser).subscribe({
      next: (data) => {
        this.chatId = data.id;
        window.sessionStorage.setItem("chat_id", this.chatId);
        window.sessionStorage.setItem("other_user", JSON.stringify(otherChatUser));
        window.sessionStorage.setItem("channel_name", `${data.chatName}`);
        window.sessionStorage.setItem("gotochat", "false");
        window.location.reload();
      },
      error: (error) => {
        console.log(error)
         alert('Error occured')
      
      }
    });

  }

}
