package com.srijon.library.controller;

import com.srijon.library.dto.BookRequestDto;
import com.srijon.library.dto.BookResponseDto;
import com.srijon.library.entity.Book;
import com.srijon.library.service.BookService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    //Logic of adding the books inside our database
    @PostMapping
    public ResponseEntity<BookResponseDto> addBook(@Valid @RequestBody  BookRequestDto bookRequestDto){
        BookResponseDto saveBook = bookService.addBook(bookRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(saveBook);

    }

    //Logic of getting all the books
    @GetMapping
    public List<BookResponseDto> getAllBooks(){

        return bookService.getAllBooks();

    }

}
