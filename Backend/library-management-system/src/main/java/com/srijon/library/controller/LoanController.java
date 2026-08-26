package com.srijon.library.controller;

import com.srijon.library.dto.Loan.LoanRequestDto;
import com.srijon.library.dto.Loan.LoanResponseDto;
import com.srijon.library.service.LoanService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    //Service object
    private final LoanService loanService;

    //Dependency injection
    public LoanController(LoanService loanService){
        this.loanService = loanService;
    }

    //Logic of borrowing a book
    @PostMapping
    public ResponseEntity<LoanResponseDto> borrowBook(
            @Valid @RequestBody LoanRequestDto loanRequestDto
            ){

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(loanService.borrowBook(loanRequestDto));
    }

    //Logic of returning a book
    @PutMapping("/{loanId}/return")
    public ResponseEntity<LoanResponseDto> returnBook(@PathVariable Long loanId){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(loanService.returnBook(loanId));
    }

    //Getting all the loans history
    @GetMapping
    public ResponseEntity<Page<LoanResponseDto>> getAllLoans(Pageable pageable){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(loanService.getAllLoans(pageable));
    }

}
