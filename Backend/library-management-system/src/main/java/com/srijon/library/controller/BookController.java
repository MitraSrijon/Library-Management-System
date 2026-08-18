package com.srijon.library.controller;

import com.srijon.library.dto.BookRequestDto;
import com.srijon.library.dto.BookResponseDto;
import com.srijon.library.entity.Book;
import com.srijon.library.service.BookService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<BookResponseDto> getAllBooks(
            @PageableDefault(size = 10) Pageable pageable){

        return bookService.getAllBooks(pageable);

    }

    //Logic of getting the books with specific id
    @GetMapping("/{id}")
    public BookResponseDto getBookById(@PathVariable Long id){

        return bookService.getBookById(id);
    }

    //Logic of updating a book info
    @PutMapping("/{id}")
    public BookResponseDto updateBook(
            @PathVariable Long id,
            @Valid @RequestBody BookRequestDto bookRequestDto
    ){
        return bookService.updateBook(id , bookRequestDto);
    }

    //Logic of deleting a book
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id){
        bookService.deleteBook(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    //Logic of searching a book bby its title,author,isbn
    @GetMapping("/search")
    public Page<BookResponseDto> searchBook(
            @RequestParam String query,
            Pageable pageable
    ){

        return bookService.searchBook(query, pageable);
    }
}
