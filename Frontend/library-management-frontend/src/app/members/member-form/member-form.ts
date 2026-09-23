import { Component, EventEmitter, inject, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MemberService } from '../../services/MemberService';
import { Member } from '../../models/member';

@Component({
  selector: 'app-member-form',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './member-form.html',
  styleUrl: './member-form.css',
})
export class MemberForm {
  name = '';
  email = '';
  phone = '';
  membershipDate = '';

  private memberService = inject(MemberService);

  @Output() memberAdded = new EventEmitter<void>();
  @Output() cancelled = new EventEmitter<void>();

  addMember(): void {
    const member: Member = {
      id: 0,
      name: this.name,
      email: this.email,
      phone: this.phone,
      membershipDate: this.membershipDate,
    };

    this.memberService.createMember(member).subscribe({
      next: (response) => {
        console.log('Member added successfully:', response);

        this.name = '';
        this.email = '';
        this.phone = '';
        this.membershipDate = '';

        this.memberAdded.emit();
      },
      error: (error) => {
        console.error('Error adding member:', error);
      },
    });
  }

  cancel(): void {
    this.cancelled.emit();
  }
}
