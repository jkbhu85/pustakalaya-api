import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TranslateModule } from '@ngx-translate/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    CommonModule,
    RouterModule,
    TranslateModule
  ],
  exports: [
    CommonModule,
    RouterModule,
    TranslateModule
  ]
})
export class AppCommonModule { }
