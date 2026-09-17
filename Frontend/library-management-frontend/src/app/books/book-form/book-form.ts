import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BookService } from '../../services/BookService';
import { inject } from '@angular/core';
import { Book } from '../../models/book';
import { Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-book-form',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './book-form.html',
  styleUrl: './book-form.css',
})
export class BookForm {
  title = '';
  author = '';
  isbn = '';
  publishedYear = 0;
  totalCopies = 0;

  private bookService = inject(BookService);

  @Output() bookAdded = new EventEmitter<void>();

  addBook(): void {
    const book: Book = {
      id: 0,
      title: this.title,
      author: this.author,
      isbn: this.isbn,
      publishedYear: this.publishedYear,
      totalCopies: this.totalCopies,
      availableCopies: 0,
    };

    this.bookService.createBook(book).subscribe({
      next: (response) => {
        console.log('Book added successfully:', response);
        this.bookAdded.emit();
      },
      error: (error) => {
        console.error('Error adding book:', error);
      },
    });
  }
}
