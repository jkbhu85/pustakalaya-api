import { NgModule } from '@angular/core';
import { LoginComponent } from './login/login.component';
import { AppSecurityRoutingModule } from './/app-security-routing.module';
import { AuthServiceService } from './auth-service.service';
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';
import { AppCommonModule } from '../modules/app-common.module';

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
    AuthServiceService
  ]
})
export class AppSecurityModule { }
