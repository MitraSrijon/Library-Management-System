import { Component, EventEmitter, OnInit, Output, inject } from '@angular/core';
import { DatePipe } from '@angular/common';
import { LoanService } from '../../services/loan-service';
import { Loan } from '../../models/loan';
import { BookService } from '../../services/BookService';
import { MemberService } from '../../services/MemberService';
import { Book } from '../../models/book';
import { Member } from '../../models/member';

@Component({
  selector: 'app-loan-list',
  standalone: true,
  imports: [DatePipe],
  templateUrl: './loan-list.html',
  styleUrl: './loan-list.css',
})
export class LoanList implements OnInit {
  loans: Loan[] = [];

  currentPage = 0;
  pageSize = 10;
  totalPages = 0;

  loading = false;
  errorMessage = '';

  private loanService = inject(LoanService);
  private bookService = inject(BookService);
  private memberService = inject(MemberService);

  books: Book[] = [];
  members: Member[] = [];

  @Output() loanReturned = new EventEmitter<void>();

  private completedRequests = 0;

  ngOnInit(): void {
    this.loadData();
  }

  loadData(): void {
    this.loading = true;
    this.errorMessage = '';
    this.completedRequests = 0;

    this.loadLoans();
    this.loadBooks();
    this.loadMembers();
  }

  loadLoans(): void {
    this.loanService.getAllLoans(this.currentPage, this.pageSize).subscribe({
      next: (response: any) => {
        this.loans = response.content;
        this.totalPages = response.totalPages;

        this.requestCompleted();
      },
      error: (error: any) => {
        console.error('Error fetching loans:', error);

        this.errorMessage = 'Unable to load loan data. Please try again.';

        this.requestCompleted();
      },
    });
  }

  loadBooks(): void {
    this.bookService.getAllBooks(0, 100).subscribe({
      next: (response: any) => {
        this.books = response.content;

        this.requestCompleted();
      },
      error: (error: any) => {
        console.error('Error fetching books:', error);

        this.errorMessage = 'Unable to load loan data. Please try again.';

        this.requestCompleted();
      },
    });
  }

  loadMembers(): void {
    this.memberService.getAllMembers(0, 100).subscribe({
      next: (response: any) => {
        this.members = response.content;

        this.requestCompleted();
      },
      error: (error: any) => {
        console.error('Error fetching members:', error);

        this.errorMessage = 'Unable to load loan data. Please try again.';

        this.requestCompleted();
      },
    });
  }

  private requestCompleted(): void {
    this.completedRequests++;

    if (this.completedRequests === 3) {
      this.loading = false;
    }
  }

  nextPage(): void {
    if (this.currentPage < this.totalPages - 1) {
      this.currentPage++;
      this.loadLoans();
    }
  }

  previousPage(): void {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.loadLoans();
    }
  }

  returnBook(loanId: number): void {
    const confirmed = confirm('Are you sure you want to return this book?');

    if (!confirmed) {
      return;
    }

    this.loanService.returnBook(loanId).subscribe({
      next: () => {
        console.log('Book returned successfully');

        this.loadLoans();
        this.loanReturned.emit();
      },
      error: (error) => {
        console.error('Error returning book:', error);
      },
    });
  }

  getMemberName(memberId: number): string {
    const member = this.members.find((member) => member.id === memberId);

    return member ? member.name : 'Unknown Member';
  }

  getBookTitle(bookId: number): string {
    const book = this.books.find((book) => book.id === bookId);

    return book ? book.title : 'Unknown Book';
  }
}
