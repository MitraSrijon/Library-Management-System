import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Book } from '../models/book';

@Injectable({
  providedIn: 'root',
})
export class BookService {
  private http = inject(HttpClient);

  private apiUrl = 'http://localhost:8080/api/books';

  getAllBooks(page: number, size: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}?page=${page}&size=${size}`);
  }

  createBook(book: Book): Observable<Book> {
    return this.http.post<Book>(this.apiUrl, book);
  }

  searchBooks(keyword: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/search?query=${keyword}`);
  }
}
