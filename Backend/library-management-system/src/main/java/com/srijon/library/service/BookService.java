package com.srijon.library.service;

import com.srijon.library.dto.BookRequestDto;
import com.srijon.library.dto.BookResponseDto;

public interface BookService {

    public BookResponseDto addBook(BookRequestDto bookRequestDto);
}
