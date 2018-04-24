import { NgModule } from '@angular/core';
import { LoginComponent } from './login/login.component';
import { AppSecurityRoutingModule } from './app-security-routing.module';
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';
import { AppCommonModule } from '../modules/app-common.module';
import { AuthService } from './auth.service';

@NgModule({
  imports: [
    AppCommonModule,
    AppSecurityRoutingModule
  ],
  declarations: [
    LoginComponent,
    ForgotPasswordComponent
  ],
  providers: [
    AuthService
  ]
})
export class AppSecurityModule { }
