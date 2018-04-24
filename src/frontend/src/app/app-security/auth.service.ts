import { Injectable } from '@angular/core';
import { UserInfo } from '../models';
import { Router } from '@angular/router';
import { NotificationService } from '../notifications/notification.service';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class AuthService {
  private readonly storage: Storage = localStorage; // sessionStorage;
  private readonly tokenLabel: string = 'json_web_token';
  private readonly userLabel: string = 'user_info';

  private user: UserInfo;
  private loggedIn: boolean;

  private user$ = new BehaviorSubject<UserInfo>(this.user);
  private loggedIn$ = new BehaviorSubject<boolean>(this.loggedIn);


  private timeoutHolder: number;
  private autoLogout = false;

  constructor(
    private router: Router,
    private notiService: NotificationService
  ) {
    this.restore();
  }


  private onAutoLogout() {
    this.autoLogout = true;

    this.logout();
    this.router.navigate(['']);
    this.notiService.danger('Your login has expired. Please login again.');

    this.timeoutHolder = 0;

    this.autoLogout = false;
    console.log('autoLogout performed');
  }


  // auto-logout is activate either from login or reload
  private activateAutoLogout() {
    if (!this.loggedIn) return;

    let timeout = (this.user.localExp - Math.floor(Date.now() / 1000)) * 1000;


    this.timeoutHolder = window.setTimeout(() => {
      this.onAutoLogout();
    }, timeout);

    console.log('autoLogout activated. time remaining: ' + timeout + ' ms');
  }


  private deactivateAutoLogout() {
    if (this.timeoutHolder) {
      window.clearTimeout(this.timeoutHolder);
      this.timeoutHolder = 0;

      console.log('autoLogout deactivated');
    }
  }


  // save JWT to storage
  private saveJwt(jwt: string) {
    this.storage.setItem(this.tokenLabel, jwt);
  }


  // remove JWT from storage
  private removeJwt() {
    this.storage.removeItem(this.tokenLabel);
  }


  // save user to this object's state and to storage
  private saveUser(userJson: string) {
    let user: UserInfo = JSON.parse(userJson);

    // check if local time of token expiration is set
    // if not set, set expiration time
    // local expiration time is set as seconds now + difference in
    // exp and iat fields
    if (!user.localExp) {
      let now = new Date();
      let seconds = Math.floor(now.getTime() / 1000);
      user.localExp = seconds + (user.exp - user.iat);
    }

    this.updateUser(user);
    this.storage.setItem(this.userLabel, JSON.stringify(user));
  }


  // removes user from this object's state and from storage
  private removeUser() {
    this.updateUser(null);
    this.storage.removeItem(this.userLabel);
  }

  private updateLoginStatus() {
    this.loggedIn = (this.user ? true : false);
    this.loggedIn$.next(this.loggedIn);
  }

  private updateUser(newUser: UserInfo) {
    this.user = newUser;
    this.user$.next(this.user);
  }


  // restore user from storage if application reloads
  // remove all information if JWT expired already.
  private restore() {
    let userJson = this.storage.getItem(this.userLabel);

    if (userJson) this.saveUser(userJson);

    // clear everything if JWT expired
    if (this.isJwtExpired()) {
      this.clearAll();
    }

    this.updateLoginStatus();
    this.activateAutoLogout();
  }


  // remove all user information and JWT from object and storage
  private clearAll() {
    this.removeJwt();
    this.removeUser();
  }


  private isJwtExpired(): boolean {
    if (!this.user) return true;

    let now = Math.floor(Date.now() / 1000);

    return now > this.user.localExp;
  }

  private getUserJsonFromJwt(jwt: string): string {
    let payload = jwt.split('.')[1];
    let base64 = payload.replace('-', '+').replace('_', '/');

    return window.atob(base64);
  }


  /**
   * Stores user authentication information (JWT) to be used by the
   * application.
   * @param jwt JWT token string
   */
  login(jwt: string): void {
    this.saveJwt(jwt);

    let userJson = this.getUserJsonFromJwt(jwt);
    this.saveUser(userJson);

    this.updateLoginStatus();
    this.activateAutoLogout();
  }


  /**
   * Returns JWT string.
   */
  getJwt(): string { return this.storage.getItem(this.tokenLabel); }


  /**
   * Returns user information wrapped inside an object.
   *
   * A wrapper is being used to take advantage of
   * Angular's change detection system.
   */
  getUserInfo(): Observable<UserInfo> { return this.user$; }


  /**
   * Returns login status wrapped inside an object.
   *
   * A wrapper is being used to take advantage of
   * Angular's change detection system.
   */
  getLoginStatus(): Observable<boolean> { return this.loggedIn$; }


  /**
   * Returns `true` if user is logged in, `false` otherwise.
   */
  isLoggedIn(): boolean { return this.loggedIn; }


  /**
   * Logs out user by removing all information.
   */
  logout(): void {
    this.clearAll();
    this.updateLoginStatus();

    if (!this.autoLogout) this.deactivateAutoLogout();
  }
}
