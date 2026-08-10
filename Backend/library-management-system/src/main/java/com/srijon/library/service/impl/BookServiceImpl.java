package com.srijon.library.service.impl;

import com.srijon.library.dto.BookRequestDto;
import com.srijon.library.entity.Book;
import com.srijon.library.mapper.BookMapper;
import com.srijon.library.repository.BookRepository;
import com.srijon.library.service.BookService;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService{

    private final BookRepository bookRepository;
    private final BookMapper mapper;

    public BookServiceImpl(BookRepository bookRepository, BookMapper mapper){
        this.bookRepository = bookRepository;
        this.mapper = mapper;
    }

    @Override
    public Book addBook(BookRequestDto bookRequestDto) {
        Book saveBook = mapper.toEntity(bookRequestDto);
        saveBook.setAvailableCopies(saveBook.getTotalCopies());

        return bookRepository.save(saveBook);
    }
}
