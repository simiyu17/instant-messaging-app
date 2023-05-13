import { Component, OnInit } from '@angular/core';
import { GlobalService } from './service/global.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit{
  title = 'chat-client';
  
  constructor(private gs: GlobalService, private route: Router){}

  ngOnInit(): void {
    if(window.sessionStorage.getItem('user')){
      this.route.navigate(['home']);
    }else {
      this.route.navigate(['login']);
    }
  }
}
