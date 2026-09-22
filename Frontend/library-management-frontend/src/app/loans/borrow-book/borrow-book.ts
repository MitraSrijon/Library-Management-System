import { Component, EventEmitter, inject, Output, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { FormsModule } from '@angular/forms';
import { LoanService } from '../../services/loan-service';
import { BookService } from '../../services/BookService';
import { MemberService } from '../../services/MemberService';
import { Book } from '../../models/book';
import { Member } from '../../models/member';

@Component({
  selector: 'app-borrow-book',
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

  private loanService = inject(LoanService);
  private bookService = inject(BookService);
  private memberService = inject(MemberService);

  ngOnInit(): void {
    this.loadBooks();
    this.loadMembers();
  }

  loadBooks(): void {
    this.bookService.getAllBooks(0, 100).subscribe({
      next: (response: any) => {
        this.books = response.content;
      },
      error: (error: any) => {
        console.error('Error fetching books:', error);
      },
    });
  }

  loadMembers(): void {
    this.memberService.getAllMembers(0, 100).subscribe({
      next: (response: any) => {
        this.members = response.content;
      },
      error: (error: any) => {
        console.error('Error fetching members:', error);
      },
    });
  }

  borrowBook(): void {
    this.loanService.borrowBook(this.memberId!, this.bookId!).subscribe({
      next: (response) => {
        console.log('Book borrowed successfully:', response);

        this.memberId = null;
        this.bookId = null;

        this.borrowForm.resetForm();

        this.loanCreated.emit();
      },
      error: (error) => {
        console.error('Error borrowing book:', error);
      },
    });
  }

  refreshData(): void {
    this.loadBooks();
    this.loadMembers();
  }
}
