package com.srijon.library.service;

import com.srijon.library.dto.Loan.LoanRequestDto;
import com.srijon.library.dto.Loan.LoanResponseDto;

public interface LoanService {

    LoanResponseDto borrowBook(LoanRequestDto loanRequestDto);
}
