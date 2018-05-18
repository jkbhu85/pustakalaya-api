import { NgModule } from '@angular/core';
import { BookRoutingModule } from './book-routing.module';
import { AddBookComponent } from './add-book/add-book.component';
import { RemoveBookComponent } from './remove-book/remove-book.component';
import { AssignBookComponent } from './assign-book/assign-book.component';
import { SearchBookComponent } from './search-book/search-book.component';
import { AppCommonModule } from '../modules/app-common.module';

@NgModule({
  imports: [
    AppCommonModule,
    BookRoutingModule
  ],
  declarations: [
    AddBookComponent,
    RemoveBookComponent,
    AssignBookComponent,
    SearchBookComponent
  ],
  exports: [
    BookRoutingModule
  ]
})
export class BookModule { }
