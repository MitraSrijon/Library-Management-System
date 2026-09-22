import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Loan } from '../models/loan';

@Injectable({
  providedIn: 'root',
})
export class LoanService {
  private http = inject(HttpClient);

  private apiUrl = 'http://localhost:8080/api/loans';

  getAllLoans(page: number, size: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}?page=${page}&size=${size}`);
  }

  borrowBook(memberId: number, bookId: number): Observable<Loan> {
    return this.http.post<Loan>(this.apiUrl, {
      memberId,
      bookId,
    });
  }

  returnBook(loanId: number): Observable<Loan> {
    return this.http.put<Loan>(`${this.apiUrl}/${loanId}/return`, {});
  }
}
