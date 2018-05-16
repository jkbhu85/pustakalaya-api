import { NgModule } from '@angular/core';
import { LoginComponent } from './login/login.component';
import { AppSecurityRoutingModule } from './app-security-routing.module';
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';
import { AppCommonModule } from '../modules/app-common.module';
import { AuthService } from './auth.service';
import { AuthGaurd } from './auth-gaurd.service';
import { NoAuthGaurd } from './no-auth-gaurd';
import { RoleAdminGaurd } from './role-admin-gaurd';
import { RoleLibrianGaurd } from './role-librarian-gaurd';
import { ProfileCompleteGaurd } from './profile-complete-gaurd';
import { ProfileIncompleteGaurd } from './gaurds/profile-incomplete-gaurd';

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
    AuthGaurd,
    NoAuthGaurd,
    RoleAdminGaurd,
    RoleLibrianGaurd,
    ProfileCompleteGaurd,
    ProfileIncompleteGaurd
  ]
})
export class AppSecurityModule { }
