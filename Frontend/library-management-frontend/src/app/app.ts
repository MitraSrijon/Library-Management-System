import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { BookList } from './books/book-list/book-list';
import { BookForm } from './books/book-form/book-form';
import { BookEdit } from './books/book-edit/book-edit';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, BookList, BookForm, BookEdit],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class App {
  protected readonly title = signal('library-management-frontend');
}
