import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';

const securityRoutes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'forgotPassword', component: ForgotPasswordComponent }
];

@NgModule({
  imports: [
    RouterModule.forChild(securityRoutes)
  ],
  exports: [
    RouterModule
  ]
})
export class AppSecurityRoutingModule { }
