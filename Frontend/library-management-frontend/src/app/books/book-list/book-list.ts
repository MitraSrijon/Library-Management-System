import { Component, OnInit, inject } from '@angular/core';
import { BookService } from '../../services/BookService';
import { Book } from '../../models/book';
import { BookForm } from '../book-form/book-form';

@Component({
  selector: 'app-book-list',
  standalone: true,
  imports: [BookForm],
  templateUrl: './book-list.html',
  styleUrl: './book-list.css',
})
export class BookList implements OnInit {
  books: Book[] = [];

  currentPage = 0;
  pageSize = 10;
  totalPages = 0;

  private bookService = inject(BookService);

  selectedBookId: number | null = null;

  showAddForm = false;

  ngOnInit(): void {
    this.loadBooks();
  }

  loadBooks(): void {
    this.bookService.getAllBooks(this.currentPage, this.pageSize).subscribe({
      next: (response: any) => {
        this.books = response.content;
        this.totalPages = response.totalPages;
      },
      error: (error: any) => {
        console.error('Error fetching books:', error);
      },
    });
  }

  nextPage(): void {
    if (this.currentPage < this.totalPages - 1) {
      this.currentPage++;
      this.loadBooks();
    }
  }

  previousPage(): void {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.loadBooks();
    }
  }

  searchBooks(keyword: string): void {
    if (!keyword.trim()) {
      this.loadBooks();
      return;
    }

    this.bookService.searchBooks(keyword).subscribe({
      next: (response: any) => {
        this.books = response.content;
      },
      error: (error: any) => {
        console.error('Error searching books:', error);
      },
    });
  }

  editBook(id: number): void {
    this.selectedBookId = id;
  }

  deleteBook(id: number): void {
    const confirmed = confirm('Are you sure you want to delete this book?');

    if (!confirmed) {
      return;
    }

    this.bookService.deleteBook(id).subscribe({
      next: () => {
        console.log('Book deleted successfully');
        this.loadBooks();
      },
      error: (error) => {
        console.error('Error deleting book:', error);
      },
    });
  }
}
