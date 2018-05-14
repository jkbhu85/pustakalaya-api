import { NgModule } from '@angular/core';

import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './app-base/home/home.component';
import { PageNotFoundComponent } from './app-base/page-not-found.component';
import { PathGaurdService } from './app-security/path-gaurd.service';

const appRoutes: Routes = [
  {
    path: 'home',
    component: HomeComponent,
    canActivate: [PathGaurdService]
  },
  { path: '', pathMatch: 'full', redirectTo: 'login' },
  { path: '**', component: PageNotFoundComponent }
];

@NgModule({
  imports: [
    RouterModule.forRoot(appRoutes)
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule { }
