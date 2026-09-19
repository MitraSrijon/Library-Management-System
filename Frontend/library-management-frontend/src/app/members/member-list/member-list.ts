import { Component, OnInit, inject } from '@angular/core';
import { MemberService } from '../../services/MemberService';
import { Member } from '../../models/member';

@Component({
  selector: 'app-member-list',
  standalone: true,
  imports: [],
  templateUrl: './member-list.html',
  styleUrl: './member-list.css',
})
export class MemberList implements OnInit {
  members: Member[] = [];

  currentPage = 0;
  pageSize = 10;
  totalPages = 0;

  selectedMemberId: number | null = null;

  private memberService = inject(MemberService);

  ngOnInit(): void {
    this.loadMembers();
  }

  loadMembers(): void {
    this.memberService.getAllMembers(this.currentPage, this.pageSize).subscribe({
      next: (response: any) => {
        this.members = response.content;
        this.totalPages = response.totalPages;
      },
      error: (error: any) => {
        console.error('Error fetching members:', error);
      },
    });
  }

  searchMembers(keyword: string): void {
    if (!keyword.trim()) {
      this.loadMembers();
      return;
    }

    this.memberService.searchMembers(keyword).subscribe({
      next: (response: any) => {
        this.members = response.content;
      },
      error: (error: any) => {
        console.error('Error searching members:', error);
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
