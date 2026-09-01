import { Component } from '@angular/core';
import { BookService } from '../../services/BookService';

@Component({
  selector: 'app-book-list',
  standalone: true,
  imports: [],
  templateUrl: './book-list.html',
  styleUrl: './book-list.css',
})
export class BookList {
  books: Book[] = [];

  constructor(private bookService: BookService) {}

  ngOnInit(): void {
    this.bookService.getAllBooks().subscribe({
      next: (response) => {
        this.books = response.content;
        console.log(this.books);
      },

      error: (error) => {
        console.error('Error fetching books:', error);
      },
    });
  }
}
