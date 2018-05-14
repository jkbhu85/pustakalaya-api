import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../app-security/auth.service';
import { Observable } from 'rxjs/Observable';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html'
})
export class HeaderComponent implements OnInit {
  loggedIn:boolean;

  constructor(
    private authService: AuthService
  ) { }

  ngOnInit() {
    this.authService
      .getLoginStatus()
      .subscribe(
        (status: boolean) => this.loggedIn = status
      );
  }

  onLogout() {
    this.authService.logout();
  }

}
