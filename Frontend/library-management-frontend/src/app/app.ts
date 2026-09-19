import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { BookList } from './books/book-list/book-list';
import { BookForm } from './books/book-form/book-form';
import { BookEdit } from './books/book-edit/book-edit';
import { MemberList } from './members/member-list/member-list';
import { MemberForm } from './members/member-form/member-form';
import { MemberEdit } from './members/member-edit/member-edit';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, BookList, BookForm, BookEdit, MemberList, MemberForm, MemberEdit],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class App {
  protected readonly title = signal('library-management-frontend');
}
