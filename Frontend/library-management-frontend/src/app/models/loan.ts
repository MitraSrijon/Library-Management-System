export interface Loan {
  id: number;
  memberId: number;
  bookId: number;
  borrowDate: string;
  dueDate: string;
  returnDate: string | null;
  status: string;
}
