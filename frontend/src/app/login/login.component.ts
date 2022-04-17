import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {AuthenticationService} from '../services/authentication.service';
import {Toast} from '@syncfusion/ej2-notifications';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  username: FormControl;
  password: FormControl;


  constructor(private formBuilder: FormBuilder, private authenticationService: AuthenticationService, private router: Router) {

    this.username = new FormControl('', [Validators.required]);
    this.loginForm = formBuilder.group({
      username: this.username,
      password: this.password
    });

  }


  ngOnInit(): void {
    // if (localStorage.getItem('loggatoAdmin') === 'true') {
    //   this.router.navigate(['admin/dashboard']);
    // }
    // if (localStorage.getItem('loggatoUser') === 'true') {
    //   this.router.navigate(['home']);
    // }
    // if (localStorage.getItem('loggatoCashier') === 'true') {
    //   this.router.navigate(['cashier']);
    // }

  }


  onSubmit(): void {
    // this.authenticationService.authenticateUserBis(this.user, this.pass).subscribe(response => {
    this.authenticationService.authenticateUser(this.loginForm.value).subscribe(response => {

      if (response.type === 'ADMIN') {
        const t = new Toast({
          title: 'Login effettuato',
          content: 'Benvenuto amministratore.',
          cssClass: 'e-toast-success'
        }); t.appendTo('#toastDiv'); t.show();
        localStorage.setItem('loggatoAdmin', 'true');
        this.router.navigate(['admin/dashboard', this.username]);
      }
      if (response.type === 'USER') {
        const t = new Toast({
          title: 'Login effettuato',
          content: 'Benvenuto',
          cssClass: 'e-toast-success'
        }); t.appendTo('#toastDiv'); t.show();
        localStorage.setItem('loggatoUser', 'true');
        this.router.navigate(['home', this.username]);
      }
      if (response.type === 'CASHIER') {
        const t = new Toast({
          title: 'Login effettuato',
          content: 'Benvenuto Cassiere',
          cssClass: 'e-toast-success'
        }); t.appendTo('#toastDiv'); t.show();
        localStorage.setItem('loggatoCashier', 'true');
        this.router.navigate(['cashier', this.username]);
      }
    }, error => {
      if (error.status == 400) {
        const t = new Toast({
          title: 'Errore login',
          content: 'Username o password sbagliate',
          cssClass: 'e-toast-danger'
        }); t.appendTo('#toastDiv'); t.show();
      }
      if (error.status == 404 || error.status == 500) {
        const t = new Toast({
          title: 'Errore',
          content: 'Ops,qualcosa è andato storto.',
          cssClass: 'e-toast-danger'
        }); t.appendTo('#toastDiv'); t.show();
      }
    });
    this.loginForm.reset();
  }

  goHome() {
    this.router.navigate(['home']);
  }
}
