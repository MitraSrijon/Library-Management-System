import { Component, EventEmitter, inject, Input, OnChanges, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MemberService } from '../../services/MemberService';

@Component({
  selector: 'app-member-edit',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './member-edit.html',
  styleUrl: './member-edit.css',
})
export class MemberEdit implements OnChanges {
  @Input() id = 0;

  name = '';
  email = '';
  phone = '';
  membershipDate = '';

  private memberService = inject(MemberService);

  @Output() memberUpdated = new EventEmitter<void>();
  @Output() cancelled = new EventEmitter<void>();

  ngOnChanges(): void {
    if (this.id > 0) {
      this.loadMember();
    }
  }

  loadMember(): void {
    this.memberService.getMemberById(this.id).subscribe({
      next: (member) => {
        this.name = member.name;
        this.email = member.email;
        this.phone = member.phone;
        this.membershipDate = member.membershipDate;
      },
      error: (error) => {
        console.error('Error loading member:', error);
      },
    });
  }

  updateMember(): void {
    const member = {
      name: this.name,
      email: this.email,
      phone: this.phone,
      membershipDate: this.membershipDate,
    };

    this.memberService.updateMember(this.id, member).subscribe({
      next: (response) => {
        console.log('Member updated successfully:', response);

        this.name = '';
        this.email = '';
        this.phone = '';
        this.membershipDate = '';

        this.memberUpdated.emit();
      },
      error: (error) => {
        console.error('Error updating member:', error);
      },
    });
  }

  cancel(): void {
    this.cancelled.emit();
  }
}
