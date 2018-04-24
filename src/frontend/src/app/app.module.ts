import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app-base/app.component';
import { AppBaseModule } from './app-base/app-base.module';
import { NotificationModule } from './notifications/notifications.module';
import { AppTranslateService } from './services/app-translate.service';
import { AppRoutingModule } from './/app-routing.module';
import { AppSecurityModule } from './app-security/app-security.module';
import { HttpClientModule, HttpClient } from '@angular/common/http';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';


export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http, 'assets/i18n/');
}


@NgModule({
  imports: [
    BrowserModule,
    HttpClientModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    }),
    NotificationModule,
    AppBaseModule,
    AppSecurityModule,
    AppRoutingModule
  ],
  providers: [AppTranslateService],
  bootstrap: [AppComponent]
})
export class AppModule { }
