import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserService } from '../service/user.service';
import { GlobalService } from '../service/global.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  msg!: string;
  userloginForm!: FormGroup;
  constructor(private fb: FormBuilder, private us: UserService, private gs: GlobalService, private router: Router) {
    this.createUserloginForm();
  }

  createUserloginForm(): void {
    this.userloginForm = this.fb.group({
      userName: [null, Validators.required],
      name: [null, Validators.required]
    });
  }

  login() {
    this.us.login(this.userloginForm.value).subscribe({
      next: (res) => {
        window.sessionStorage.setItem('user', JSON.stringify(res));
        this.router.navigate(['home']);
      },
      error: (error) => {
        console.log(error);
        this.msg = "Unable to login";
        if (error.error.message) {
          this.msg = error.error.message;
        }
        alert(this.msg);
      }
    });
  }

  signup() {
    this.us.register(this.userloginForm.value).subscribe({
      next: (res) => {
        this.login();
      },
      error: (error) => {
        console.log(error);
        this.msg = "Unable to signup!!";
        if (error.error.message) {
          this.msg = error.error.message;
        }
        alert(this.msg);
      }
    });
  }


}
