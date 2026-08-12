package com.srijon.library.service;

import com.srijon.library.dto.BookRequestDto;
import com.srijon.library.dto.BookResponseDto;

import java.util.List;

public interface BookService {

    //Logic of adding the books inside our database
    public BookResponseDto addBook(BookRequestDto bookRequestDto);

    //Logic of getting all the info of all the books present in our database
    public List<BookResponseDto> getAllBooks();


}
