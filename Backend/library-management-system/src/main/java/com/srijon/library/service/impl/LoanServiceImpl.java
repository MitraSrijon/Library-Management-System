package com.srijon.library.service.impl;

import com.srijon.library.dto.Loan.LoanRequestDto;
import com.srijon.library.dto.Loan.LoanResponseDto;
import com.srijon.library.entity.Book;
import com.srijon.library.entity.Loan;
import com.srijon.library.entity.Member;
import com.srijon.library.entity.enums.LoanStatus;
import com.srijon.library.exception.BookNotAvailableException;
import com.srijon.library.exception.BookNotFoundException;
import com.srijon.library.exception.MemberNotFoundException;
import com.srijon.library.mapper.LoanMapper;
import com.srijon.library.repository.BookRepository;
import com.srijon.library.repository.LoanRepository;
import com.srijon.library.repository.MemberRepository;
import com.srijon.library.service.LoanService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class LoanServiceImpl implements LoanService {

    //Dependency injection
    private final LoanRepository loanRepository;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;
    private final LoanMapper loanMapper;

    //Constructor Injection
    public LoanServiceImpl(
            LoanRepository loanRepository,
            MemberRepository memberRepository,
            BookRepository bookRepository,
            LoanMapper loanMapper){

        this.loanRepository = loanRepository;
        this.memberRepository = memberRepository;
        this.bookRepository = bookRepository;
        this.loanMapper = loanMapper;
    }


    @Override
    public LoanResponseDto borrowBook(LoanRequestDto loanRequestDto) {

        //Fetching Member
        Member member = memberRepository.findById(loanRequestDto.getMemberId())
                .orElseThrow(
                        () -> new MemberNotFoundException("Member not found with id : " + loanRequestDto.getMemberId())
                );

        //Fetching Book
        Book book = bookRepository.findById(loanRequestDto.getBookId())
                .orElseThrow(
                        () -> new BookNotFoundException("Book not found with id :" + loanRequestDto.getBookId())
                );

        //Checking if book is available i.e. more than 1
        if(book.getAvailableCopies() <= 0){
            throw new BookNotAvailableException(
                    "Book is not available for borrowing"
            );
        }
        else{
            book.setAvailableCopies(book.getAvailableCopies() - 1);
        }

        //Saving the updated book
        bookRepository.save(book);

        Loan loan = loanMapper.toEntity(loanRequestDto,member,book);

        loan.setBorrowDate(LocalDate.now());
        loan.setDueDate(LocalDate.now().plusDays(14));
        loan.setStatus(LoanStatus.BORROWED);

        //Saving the loan data
        Loan savedLoan = loanRepository.save(loan);

        return loanMapper.toResponseDto(savedLoan);
    }
}
