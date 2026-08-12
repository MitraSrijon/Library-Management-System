package com.srijon.library.service.impl;

import com.srijon.library.dto.BookRequestDto;
import com.srijon.library.dto.BookResponseDto;
import com.srijon.library.entity.Book;
import com.srijon.library.mapper.BookMapper;
import com.srijon.library.repository.BookRepository;
import com.srijon.library.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService{

    private final BookRepository bookRepository;
    private final BookMapper mapper;

    public BookServiceImpl(BookRepository bookRepository, BookMapper mapper){
        this.bookRepository = bookRepository;
        this.mapper = mapper;
    }

    //Logic of adding the books inside our database
    @Override
    public BookResponseDto addBook(BookRequestDto bookRequestDto) {
        Book saveBook = mapper.toEntity(bookRequestDto);
        saveBook.setAvailableCopies(saveBook.getTotalCopies());

        bookRepository.save(saveBook);
        return mapper.toResponseDto(saveBook);
    }

    //Getting all the books that are present in database
    @Override
    public List<BookResponseDto> getAllBooks() {

        List<Book> books = bookRepository.findAll();

        //Selects every book from books list , converts it into bookResponseDto and gives back
        // a list of bookResponseDto object
        return books
                .stream()
                .map(mapper :: toResponseDto)
                .toList();
    }
}
