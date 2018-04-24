import { Injectable } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class AppTranslateService {
  readonly supportedLangs = ['en-US', 'hi-IN'];
  readonly defaultLang = this.supportedLangs[1];

  constructor(
    private translate: TranslateService
  ) {
    this.translate.setDefaultLang(this.defaultLang);
    this.translate.use(this.defaultLang);
  }


  public getTranslation(key: string): Observable<any> {
    return this.translate.getTranslation(key);
  }

}
