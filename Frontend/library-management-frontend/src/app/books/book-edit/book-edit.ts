import { Component, EventEmitter, Input, inject, OnInit, OnChanges, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BookService } from '../../services/BookService';

@Component({
  selector: 'app-book-edit',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './book-edit.html',
  styleUrl: './book-edit.css',
})
export class BookEdit implements OnInit, OnChanges {
  @Input() id = 0;
  title = '';
  author = '';
  isbn = '';
  publishedYear = 0;
  totalCopies = 0;

  private bookService = inject(BookService);

  @Output() bookUpdated = new EventEmitter<void>();

  ngOnInit(): void {
    this.loadBook();
  }

  loadBook(): void {
    this.bookService.getBookById(this.id).subscribe({
      next: (book) => {
        this.title = book.title;
        this.author = book.author;
        this.isbn = book.isbn;
        this.publishedYear = book.publishedYear;
        this.totalCopies = book.totalCopies;
      },
      error: (error) => {
        console.error('Error loading book:', error);
      },
    });
  }

  updateBook(): void {
    const book = {
      title: this.title,
      author: this.author,
      isbn: this.isbn,
      publishedYear: this.publishedYear,
      totalCopies: this.totalCopies,
    };

    this.bookService.updateBook(this.id, book).subscribe({
      next: (response) => {
        console.log('Book updated successfully:', response);

        this.title = '';
        this.author = '';
        this.isbn = '';
        this.publishedYear = 0;
        this.totalCopies = 0;

        this.bookUpdated.emit();
      },
      error: (error) => {
        console.error('Error updating book:', error);
      },
    });
  }

  ngOnChanges(): void {
    if (this.id > 0) {
      this.loadBook();
    }
  }
}
