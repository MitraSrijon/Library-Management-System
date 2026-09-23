import { Component, EventEmitter, inject, Output, ViewChild } from '@angular/core';
import { NgForm, FormsModule } from '@angular/forms';
import { LoanService } from '../../services/loan-service';
import { BookService } from '../../services/BookService';
import { MemberService } from '../../services/MemberService';
import { Book } from '../../models/book';
import { Member } from '../../models/member';

@Component({
  selector: 'app-borrow-book',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './borrow-book.html',
  styleUrl: './borrow-book.css',
})
export class BorrowBook {
  memberId: number | null = null;
  bookId: number | null = null;

  @Output() loanCreated = new EventEmitter<void>();

  @ViewChild('borrowForm') borrowForm!: NgForm;

  books: Book[] = [];
  members: Member[] = [];

  loading = false;
  submitting = false;
  errorMessage = '';

  private completedRequests = 0;

  private loanService = inject(LoanService);
  private bookService = inject(BookService);
  private memberService = inject(MemberService);

  ngOnInit(): void {
    this.loadData();
  }

  loadData(): void {
    this.loading = true;
    this.errorMessage = '';
    this.completedRequests = 0;

    this.loadBooks();
    this.loadMembers();
  }

  loadBooks(): void {
    this.bookService.getAllBooks(0, 100).subscribe({
      next: (response: any) => {
        this.books = response.content;
        this.requestCompleted();
      },
      error: (error: any) => {
        console.error('Error fetching books:', error);
        this.errorMessage = 'Unable to load borrow data. Please try again.';
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
        this.errorMessage = 'Unable to load borrow data. Please try again.';
        this.requestCompleted();
      },
    });
  }

  private requestCompleted(): void {
    this.completedRequests++;

    if (this.completedRequests === 2) {
      this.loading = false;
    }
  }

  borrowBook(): void {
    if (this.borrowForm.invalid || this.submitting) {
      return;
    }

    this.submitting = true;
    this.errorMessage = '';

    this.loanService.borrowBook(this.memberId!, this.bookId!).subscribe({
      next: (response) => {
        console.log('Book borrowed successfully:', response);

        this.memberId = null;
        this.bookId = null;

        this.borrowForm.resetForm();

        this.submitting = false;

        this.loanCreated.emit();
      },
      error: (error) => {
        console.error('Error borrowing book:', error);

        this.submitting = false;
        this.errorMessage = error?.error?.message || 'Unable to borrow the book. Please try again.';
      },
    });
  }

  refreshData(): void {
    this.loadData();
  }
}
