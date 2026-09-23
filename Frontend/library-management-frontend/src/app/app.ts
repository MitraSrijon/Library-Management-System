import { Component, signal } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { BookList } from './books/book-list/book-list';
import { BookForm } from './books/book-form/book-form';
import { BookEdit } from './books/book-edit/book-edit';
import { MemberList } from './members/member-list/member-list';
import { MemberForm } from './members/member-form/member-form';
import { MemberEdit } from './members/member-edit/member-edit';
import { LoanList } from './loans/loan-list/loan-list';
import { BorrowBook } from './loans/borrow-book/borrow-book';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet,
    BookList,
    BookForm,
    BookEdit,
    MemberList,
    MemberForm,
    MemberEdit,
    LoanList,
    BorrowBook,
    RouterLink,
    RouterLinkActive,
  ],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class App {
  protected readonly title = signal('library-management-frontend');
}
