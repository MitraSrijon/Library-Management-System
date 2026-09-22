import { Routes } from '@angular/router';
import { Dashboard } from './dashboard/dashboard';
import { BookList } from './books/book-list/book-list';
import { MemberList } from './members/member-list/member-list';
import { LoanList } from './loans/loan-list/loan-list';
import { BorrowBook } from './loans/borrow-book/borrow-book';

export const routes: Routes = [
  {
    path: '',
    component: Dashboard,
  },
  {
    path: 'books',
    component: BookList,
  },

  {
    path: 'members',
    component: MemberList,
  },
  {
    path: 'loans',
    component: LoanList,
  },

  {
    path: 'borrow',
    component: BorrowBook,
  },
];
