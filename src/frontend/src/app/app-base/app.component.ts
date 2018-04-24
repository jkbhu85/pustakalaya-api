import { Component, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { AppTranslateService } from '../services/app-translate.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html'
})
export class AppComponent {
  loggedIn = false;


  constructor(
    private translate: AppTranslateService
  ) { }
}
