import { Component, OnInit, inject } from '@angular/core';
import { RouterLink } from '@angular/router';
import { BookService } from '../services/BookService';
import { MemberService } from '../services/MemberService';
import { LoanService } from '../services/loan-service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class Dashboard implements OnInit {
  totalBooks = 0;
  totalMembers = 0;
  activeLoans = 0;
  availableBooks = 0;

  loading = true;

  private bookService = inject(BookService);
  private memberService = inject(MemberService);
  private loanService = inject(LoanService);

  ngOnInit(): void {
    this.loadDashboardData();
  }

  loadDashboardData(): void {
    this.loading = true;

    this.bookService.getAllBooks(0, 1000).subscribe({
      next: (response) => {
        const books = response.content ?? [];

        this.totalBooks = response.totalElements ?? books.length;

        this.availableBooks = books.reduce(
          (total: number, book: any) => total + book.availableCopies,
          0,
        );

        this.checkLoadingComplete();
      },

      error: (error) => {
        console.error('Error loading books:', error);

        this.checkLoadingComplete();
      },
    });

    this.memberService.getAllMembers(0, 1000).subscribe({
      next: (response) => {
        this.totalMembers = response.totalElements ?? response.content?.length ?? 0;

        this.checkLoadingComplete();
      },

      error: (error) => {
        console.error('Error loading members:', error);

        this.checkLoadingComplete();
      },
    });

    this.loanService.getAllLoans(0, 1000).subscribe({
      next: (response) => {
        const loans = response.content ?? [];

        this.activeLoans = loans.filter((loan: any) => loan.status === 'BORROWED').length;

        this.checkLoadingComplete();
      },

      error: (error) => {
        console.error('Error loading loans:', error);

        this.checkLoadingComplete();
      },
    });
  }

  private completedRequests = 0;

  private checkLoadingComplete(): void {
    this.completedRequests++;

    if (this.completedRequests === 3) {
      this.loading = false;
    }
  }
}
