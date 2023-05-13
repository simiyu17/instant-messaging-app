import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { ChatComponent } from './chat/chat.component';
import { MainComponent } from './main/main.component';

const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'home', component: MainComponent},
  {path: 'chats', component: ChatComponent},
  {path: '', redirectTo: '/home', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
