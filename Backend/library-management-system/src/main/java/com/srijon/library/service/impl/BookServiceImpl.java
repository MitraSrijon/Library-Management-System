package com.srijon.library.service.impl;

import com.srijon.library.entity.Book;
import com.srijon.library.repository.BookRepository;
import com.srijon.library.service.BookService;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService{

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    @Override
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }
}
