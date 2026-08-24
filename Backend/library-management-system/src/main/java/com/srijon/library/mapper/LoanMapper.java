package com.srijon.library.mapper;

import com.srijon.library.dto.Loan.LoanRequestDto;
import com.srijon.library.dto.Loan.LoanResponseDto;
import com.srijon.library.entity.Book;
import com.srijon.library.entity.Loan;
import com.srijon.library.entity.Member;
import org.springframework.stereotype.Component;

@Component
public class LoanMapper {

    public Loan toEntity(
            LoanRequestDto loanRequestDto,
            Member member,
            Book book
    ){

        Loan loan = new Loan();

        loan.setMember(member);
        loan.setBook(book);


        return loan;
    }

    public LoanResponseDto toResponseDto(Loan loan){

        LoanResponseDto loanResponseDto = new LoanResponseDto();

        loanResponseDto.setId(loan.getId());
        loanResponseDto.setMemberId(loan.getMember().getId());
        loanResponseDto.setBookId(loan.getBook().getId());
        loanResponseDto.setBorrowDate(loan.getBorrowDate());
        loanResponseDto.setDueDate(loan.getDueDate());
        loanResponseDto.setReturnDate(loan.getReturnDate());
        loanResponseDto.setStatus(loan.getStatus());

        return loanResponseDto;
    }
}