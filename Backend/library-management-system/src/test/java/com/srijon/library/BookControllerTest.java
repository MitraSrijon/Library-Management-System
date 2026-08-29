package com.srijon.library;

import com.srijon.library.controller.BookController;
import com.srijon.library.dto.Book.BookRequestDto;
import com.srijon.library.dto.Book.BookResponseDto;
import com.srijon.library.exception.BookNotFoundException;
import com.srijon.library.service.BookService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private BookService bookService;


    // =========================================================
    // 1. ADD BOOK - SUCCESS
    // =========================================================

    @Test
    void addBook_shouldReturnCreated() throws Exception {

        BookRequestDto request = new BookRequestDto();

        request.setTitle("Clean Code");
        request.setAuthor("Robert Martin");
        request.setIsbn("9780132350884");
        request.setPublishedYear(2008);
        request.setTotalCopies(5);


        BookResponseDto response = new BookResponseDto();

        response.setId(1L);
        response.setTitle("Clean Code");
        response.setAuthor("Robert Martin");
        response.setIsbn("9780132350884");
        response.setPublishedYear(2008);
        response.setTotalCopies(5);
        response.setAvailableCopies(5);


        when(bookService.addBook(any(BookRequestDto.class)))
                .thenReturn(response);


        mockMvc.perform(
                        post("/api/books")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Clean Code"))
                .andExpect(jsonPath("$.author").value("Robert Martin"))
                .andExpect(jsonPath("$.isbn").value("9780132350884"))
                .andExpect(jsonPath("$.publishedYear").value(2008))
                .andExpect(jsonPath("$.totalCopies").value(5))
                .andExpect(jsonPath("$.availableCopies").value(5));
    }


    // =========================================================
    // 2. GET ALL BOOKS - SUCCESS
    // =========================================================

    @Test
    void getAllBooks_shouldReturnBooks() throws Exception {

        BookResponseDto response = new BookResponseDto();

        response.setId(1L);
        response.setTitle("Clean Code");
        response.setAuthor("Robert Martin");
        response.setIsbn("9780132350884");
        response.setPublishedYear(2008);
        response.setTotalCopies(5);
        response.setAvailableCopies(5);


        Page<BookResponseDto> page =
                new PageImpl<>(List.of(response));


        when(bookService.getAllBooks(any(Pageable.class)))
                .thenReturn(page);


        mockMvc.perform(
                        get("/api/books")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].title")
                        .value("Clean Code"))
                .andExpect(jsonPath("$.content[0].author")
                        .value("Robert Martin"))
                .andExpect(jsonPath("$.content[0].isbn")
                        .value("9780132350884"))
                .andExpect(jsonPath("$.content[0].totalCopies")
                        .value(5))
                .andExpect(jsonPath("$.content[0].availableCopies")
                        .value(5));
    }


    // =========================================================
    // 3. GET BOOK BY ID - SUCCESS
    // =========================================================

    @Test
    void getBookById_shouldReturnBook() throws Exception {

        BookResponseDto response = new BookResponseDto();

        response.setId(1L);
        response.setTitle("Clean Code");
        response.setAuthor("Robert Martin");
        response.setIsbn("9780132350884");
        response.setPublishedYear(2008);
        response.setTotalCopies(5);
        response.setAvailableCopies(5);


        when(bookService.getBookById(1L))
                .thenReturn(response);


        mockMvc.perform(
                        get("/api/books/{id}", 1L)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title")
                        .value("Clean Code"))
                .andExpect(jsonPath("$.author")
                        .value("Robert Martin"))
                .andExpect(jsonPath("$.isbn")
                        .value("9780132350884"))
                .andExpect(jsonPath("$.totalCopies")
                        .value(5))
                .andExpect(jsonPath("$.availableCopies")
                        .value(5));
    }


    // =========================================================
    // 4. GET BOOK BY ID - NOT FOUND
    // =========================================================

    @Test
    void getBookById_shouldReturnNotFound() throws Exception {

        when(bookService.getBookById(999L))
                .thenThrow(
                        new BookNotFoundException(
                                "Book not found with ID : 999"
                        )
                );


        mockMvc.perform(
                        get("/api/books/{id}", 999L)
                )
                .andExpect(status().isNotFound());
    }


    // =========================================================
    // 5. UPDATE BOOK - SUCCESS
    // =========================================================

    @Test
    void updateBook_shouldReturnUpdatedBook() throws Exception {

        BookRequestDto request = new BookRequestDto();

        request.setTitle("Clean Code Updated");
        request.setAuthor("Robert Martin");
        request.setIsbn("9780132350884");
        request.setPublishedYear(2008);
        request.setTotalCopies(7);


        BookResponseDto response = new BookResponseDto();

        response.setId(1L);
        response.setTitle("Clean Code Updated");
        response.setAuthor("Robert Martin");
        response.setIsbn("9780132350884");
        response.setPublishedYear(2008);
        response.setTotalCopies(7);
        response.setAvailableCopies(7);


        when(bookService.updateBook(
                eq(1L),
                any(BookRequestDto.class)
        )).thenReturn(response);


        mockMvc.perform(
                        put("/api/books/{id}", 1L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title")
                        .value("Clean Code Updated"))
                .andExpect(jsonPath("$.totalCopies")
                        .value(7))
                .andExpect(jsonPath("$.availableCopies")
                        .value(7));
    }


    // =========================================================
    // 6. UPDATE BOOK - NOT FOUND
    // =========================================================

    @Test
    void updateBook_shouldReturnNotFound() throws Exception {

        BookRequestDto request = new BookRequestDto();

        request.setTitle("Clean Code");
        request.setAuthor("Robert Martin");
        request.setIsbn("9780132350884");
        request.setPublishedYear(2008);
        request.setTotalCopies(5);


        when(bookService.updateBook(
                eq(999L),
                any(BookRequestDto.class)
        )).thenThrow(
                new BookNotFoundException(
                        "Book not found with ID : 999"
                )
        );


        mockMvc.perform(
                        put("/api/books/{id}", 999L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isNotFound());
    }


    // =========================================================
    // 7. DELETE BOOK - SUCCESS
    // =========================================================

    @Test
    void deleteBook_shouldReturnNoContent() throws Exception {

        doNothing()
                .when(bookService)
                .deleteBook(1L);


        mockMvc.perform(
                        delete("/api/books/{id}", 1L)
                )
                .andExpect(status().isNoContent());


        verify(bookService).deleteBook(1L);
    }


    // =========================================================
    // 8. SEARCH BOOK - SUCCESS
    // =========================================================

    @Test
    void searchBook_shouldReturnMatchingBooks() throws Exception {

        BookResponseDto response = new BookResponseDto();

        response.setId(1L);
        response.setTitle("Clean Code");
        response.setAuthor("Robert Martin");
        response.setIsbn("9780132350884");
        response.setPublishedYear(2008);
        response.setTotalCopies(5);
        response.setAvailableCopies(5);


        Page<BookResponseDto> page =
                new PageImpl<>(List.of(response));


        when(bookService.searchBook(
                eq("Clean"),
                any(Pageable.class)
        )).thenReturn(page);


        mockMvc.perform(
                        get("/api/books/search")
                                .param("query", "Clean")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title")
                        .value("Clean Code"))
                .andExpect(jsonPath("$.content[0].author")
                        .value("Robert Martin"))
                .andExpect(jsonPath("$.content[0].isbn")
                        .value("9780132350884"));
    }


    // =========================================================
    // 9. SEARCH BOOK - NO RESULTS
    // =========================================================

    @Test
    void searchBook_shouldReturnEmptyPage() throws Exception {

        Page<BookResponseDto> emptyPage =
                Page.empty();


        when(bookService.searchBook(
                eq("xyz"),
                any(Pageable.class)
        )).thenReturn(emptyPage);


        mockMvc.perform(
                        get("/api/books/search")
                                .param("query", "xyz")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isEmpty());
    }
}