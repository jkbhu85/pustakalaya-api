import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { RoleLibrianGaurd } from '../app-security/role-librarian-gaurd';
import { AddUserComponent } from './add-user/add-user.component';
import { ModifyUserComponent } from './modify-user/modify-user.component';

const userRoutes: Routes = [
  {
    path: 'user',
    canActivateChild: [RoleLibrianGaurd],
    children: [
      {
        path: '',
        redirectTo: 'add',
        pathMatch: 'full'
      },
      {
        path: 'add',
        component: AddUserComponent
      },
      {
        path: 'modify',
        component: ModifyUserComponent
      }
    ]
  }
];

@NgModule({
  imports: [
    RouterModule.forChild(userRoutes)
  ],
  exports: [
    RouterModule
  ]
})
export class UserRoutingModule { }
