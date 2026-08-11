package com.srijon.library.controller;

import com.srijon.library.dto.BookRequestDto;
import com.srijon.library.dto.BookResponseDto;
import com.srijon.library.entity.Book;
import com.srijon.library.service.BookService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @PostMapping
    public BookResponseDto addBook(@Valid @RequestBody  BookRequestDto bookRequestDto){
        return bookService.addBook(bookRequestDto);
    }

}
