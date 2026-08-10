package com.srijon.library.service;

import com.srijon.library.dto.BookRequestDto;
import com.srijon.library.entity.Book;

public interface BookService {

    public Book addBook(BookRequestDto bookRequestDto);
}
