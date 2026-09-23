import { Component, OnInit, inject } from '@angular/core';
import { DatePipe } from '@angular/common';
import { MemberService } from '../../services/MemberService';
import { Member } from '../../models/member';
import { MemberForm } from '../member-form/member-form';
import { MemberEdit } from '../member-edit/member-edit';

@Component({
  selector: 'app-member-list',
  standalone: true,
  imports: [MemberForm, MemberEdit, DatePipe],
  templateUrl: './member-list.html',
  styleUrl: './member-list.css',
})
export class MemberList implements OnInit {
  members: Member[] = [];

  currentPage = 0;
  pageSize = 10;
  totalPages = 0;

  loading = false;
  errorMessage = '';

  selectedMemberId: number | null = null;
  showAddForm = false;

  private memberService = inject(MemberService);

  ngOnInit(): void {
    this.loadMembers();
  }

  loadMembers(): void {
    this.loading = true;
    this.errorMessage = '';

    this.memberService.getAllMembers(this.currentPage, this.pageSize).subscribe({
      next: (response: any) => {
        this.members = response.content;
        this.totalPages = response.totalPages;
        this.loading = false;
      },
      error: (error: any) => {
        console.error('Error fetching members:', error);

        this.loading = false;
        this.errorMessage = 'Unable to load members. Please try again.';
      },
    });
  }

  searchMembers(keyword: string): void {
    if (!keyword.trim()) {
      this.loadMembers();
      return;
    }

    this.loading = true;
    this.errorMessage = '';

    this.memberService.searchMembers(keyword).subscribe({
      next: (response: any) => {
        this.members = response.content;
        this.loading = false;
      },
      error: (error: any) => {
        console.error('Error searching members:', error);

        this.loading = false;
        this.errorMessage = 'Unable to search members. Please try again.';
      },
    });
  }

  nextPage(): void {
    if (this.currentPage < this.totalPages - 1) {
      this.currentPage++;
      this.loadMembers();
    }
  }

  previousPage(): void {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.loadMembers();
    }
  }

  editMember(id: number): void {
    this.selectedMemberId = id;
  }

  deleteMember(id: number): void {
    const confirmed = confirm('Are you sure you want to delete this member?');

    if (!confirmed) {
      return;
    }

    this.memberService.deleteMember(id).subscribe({
      next: () => {
        console.log('Member deleted successfully');
        this.loadMembers();
      },
      error: (error) => {
        console.error('Error deleting member:', error);
      },
    });
  }
}
