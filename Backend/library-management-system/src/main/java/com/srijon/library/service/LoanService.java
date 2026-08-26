package com.srijon.library.service;

import com.srijon.library.dto.Loan.LoanRequestDto;
import com.srijon.library.dto.Loan.LoanResponseDto;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

public interface LoanService {

    //Logic of borrowing a book
    LoanResponseDto borrowBook(LoanRequestDto loanRequestDto);

    //Logic of returning a book
    LoanResponseDto returnBook(Long loanId);

    //Loan History
    Page<LoanResponseDto> getAllLoans(Pageable pageable);
}
