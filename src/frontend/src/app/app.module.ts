import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';


import { AppComponent } from './app-base/app.component';
import { AppBaseModule } from './app-base/app-base.module';
import { NotificationModule } from './notifications/notifications.module';
import { AppTranslateService } from './services/app-translate.service';
import { AppRoutingModule } from './/app-routing.module';


@NgModule({
  imports: [
    BrowserModule,
    AppBaseModule,
    NotificationModule,
    AppRoutingModule
  ],
  providers: [AppTranslateService],
  bootstrap: [AppComponent]
})
export class AppModule { }
