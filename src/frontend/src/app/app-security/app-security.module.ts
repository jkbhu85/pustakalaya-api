import { NgModule } from '@angular/core';
import { LoginComponent } from './login/login.component';
import { AppSecurityRoutingModule } from './app-security-routing.module';
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';
import { AppCommonModule } from '../modules/app-common.module';
import { AuthService } from './auth.service';
import { PathGaurdService } from './auth-gaurd.service';
import { NoAuthGaurdService } from './no-auth-gaurd';

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
    AuthService,
    PathGaurdService,
    NoAuthGaurdService
  ]
})
export class AppSecurityModule { }
