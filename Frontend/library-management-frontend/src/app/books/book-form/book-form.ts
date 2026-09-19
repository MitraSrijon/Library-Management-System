import { Component, EventEmitter, inject, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BookService } from '../../services/BookService';
import { Book } from '../../models/book';

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
  publishedYear: number | null = null;
  totalCopies: number | null = null;

  private bookService = inject(BookService);

  @Output() bookAdded = new EventEmitter<void>();

  addBook(): void {
    const book: Book = {
      id: 0,
      title: this.title,
      author: this.author,
      isbn: this.isbn,
      publishedYear: this.publishedYear!,
      totalCopies: this.totalCopies!,
      availableCopies: 0,
    };

    this.bookService.createBook(book).subscribe({
      next: (response) => {
        console.log('Book added successfully:', response);

        this.title = '';
        this.author = '';
        this.isbn = '';
        this.publishedYear = null;
        this.totalCopies = null;

        this.bookAdded.emit();
      },
      error: (error) => {
        console.error('Error adding book:', error);
      },
    });
  }
}
