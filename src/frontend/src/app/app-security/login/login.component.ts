import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { BASE_HREF } from '../../consts';

const LOGIN_URL = BASE_HREF + '/api/login';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styles: []
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  submitted = false;

  constructor(
    private fb: FormBuilder,
    private http: HttpClient
  ) {
    this.createForm();
  }

  createForm(): void {
    this.loginForm = this.fb.group({
      userId: ['', [Validators.required]],
      password: ['', Validators.required]
    });
  }

  get userId() {
    return this.loginForm.get('userId');
  }

  get password() {
    return this.loginForm.get('password');
  }

  ngOnInit() {
  }

  prepareData() {
    let l: any = {};

    l.userId = this.userId.value;
    l.password = this.password.value;

    return l;
  }

  onSubmit() {
    if (this.submitted) return;

    this.submitted = true;

    const data = this.prepareData();

    this.http
      .post(LOGIN_URL, data, {observe: 'response', responseType: 'text'})
      .subscribe(
        (response: HttpResponse<string>) => this.handleResponse(response)
      );

    this.submitted = false;
  }

  private handleResponse(response: HttpResponse<string>) {
    switch (response.status) {
      case 200:
        // login successful
        break;
      case 490:
        // invalid credentials
        break;
      default:
        // some error occurred
    }
  }

}
