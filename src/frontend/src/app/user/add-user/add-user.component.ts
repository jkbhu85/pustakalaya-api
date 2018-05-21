import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Observable, Subscription } from 'rxjs';
import { AppTranslateService } from '../../services/app-translate.service';
import { HttpResponse, HttpClient } from '@angular/common/http';
import { BASE_HREF } from '../../consts';

const ADD_USER_URL = BASE_HREF + '/user';

@Component({
  templateUrl: './add-user.component.html',
  styles: []
})
export class AddUserComponent implements OnInit {

  addUserForm: FormGroup;
  submitted = false;
  showError = false;
  errorText$: Observable<any>;
  private formValueChangeSubscription: Subscription;
  private debug = true;

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private translate: AppTranslateService
  ) {
    this.createForm();
  }

  ngOnInit() {
  }

  createForm() {
    this.addUserForm = this.fb.group({
      'firstName': ['', [Validators.required]],
      'lastName': ['', [Validators.required]],
      'email': ['', [Validators.required, Validators.email]]
    });
  }

  get firstName() {
    return this.addUserForm.get('firstName');
  }

  get lastName() {
    return this.addUserForm.get('lastName');
  }

  get email() {
    return this.addUserForm.get('email');
  }

  prepareData() {
    let l: any = {};

    l.firstName = this.firstName.value;
    l.lastName = this.lastName.value;
    l.email = this.email.value;

    return l;
  }

  onSubmit() {
    if (this.submitted) return;

    if (this.addUserForm.invalid) return;

    if (this.debug) {
      return;
    }

    this.submitted = true;

    const data = this.prepareData();

    this.http
      .post(ADD_USER_URL, data, {observe: 'response', responseType: 'text'})
      .subscribe(
        (response: HttpResponse<string>) => this.handleResponse(response),
        (response: HttpResponse<string>) => this.handleResponse(response)
      );
  }


  private handleResponse(response: HttpResponse<string>) {
    this.submitted = false;
    
    switch (response.status) {
      case 200:
        // login successful
        let jwt = response.body;
        console.log('login successful');
        break;
      case 400:

        break;
      default:
        // some error occurred
        this.showLoginError('common.errorOccurred');
    }
  }

  private showLoginError(msgKey: string) {
    this.showError = true;
    this.errorText$ = this.translate.get(msgKey);
    this.formValueChangeSubscription = this.addUserForm.valueChanges.subscribe(() => this.hideLoginError())
  }

  private hideLoginError() {
    this.showError = false;
    this.errorText$ = undefined;
    this.formValueChangeSubscription.unsubscribe();
  }

}
