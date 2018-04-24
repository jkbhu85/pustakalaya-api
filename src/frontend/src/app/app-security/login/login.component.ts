import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styles: []
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;

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

  onSubmit() { }

}
