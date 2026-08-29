package com.srijon.library;

import com.srijon.library.dto.Book.BookRequestDto;
import com.srijon.library.dto.Book.BookResponseDto;
import com.srijon.library.entity.Book;
import com.srijon.library.exception.BookDeletionException;
import com.srijon.library.exception.BookNotFoundException;
import com.srijon.library.mapper.BookMapper;
import com.srijon.library.repository.BookRepository;
import com.srijon.library.service.impl.BookServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    void addBook_shouldSaveBookSuccessfully() {

        // Arrange
        BookRequestDto bookRequestDto = new BookRequestDto();

        bookRequestDto.setTitle("Clean Code");
        bookRequestDto.setAuthor("Robert Martin");
        bookRequestDto.setIsbn("9780132350884");
        bookRequestDto.setPublishedYear(2008);
        bookRequestDto.setTotalCopies(5);

        Book book = new Book();

        book.setTitle("Clean Code");
        book.setAuthor("Robert Martin");
        book.setIsbn("9780132350884");
        book.setPublishedYear(2008);
        book.setTotalCopies(5);

        BookResponseDto bookResponseDto = new BookResponseDto();

        bookResponseDto.setTitle("Clean Code");
        bookResponseDto.setAuthor("Robert Martin");
        bookResponseDto.setIsbn("9780132350884");
        bookResponseDto.setPublishedYear(2008);
        bookResponseDto.setTotalCopies(5);
        bookResponseDto.setAvailableCopies(5);


        when(bookMapper.toEntity(bookRequestDto)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.toResponseDto(book)).thenReturn(bookResponseDto);

        // Act
        BookResponseDto result = bookService.addBook(bookRequestDto);

        // Assert
        assertEquals("Clean Code", result.getTitle());
        assertEquals(5, result.getTotalCopies());
        assertEquals(5, result.getAvailableCopies());

        verify(bookMapper).toEntity(bookRequestDto);
        verify(bookRepository).save(book);
    }

    @Test
    void getAllBooks_shouldReturnBooksSuccessfully() {

        // Arrange
        Pageable pageable = PageRequest.of(0, 10);

        Book book = new Book();
        book.setTitle("Clean Code");

        BookResponseDto responseDto = new BookResponseDto();
        responseDto.setTitle("Clean Code");

        Page<Book> bookPage = new PageImpl<>(List.of(book));

        when(bookRepository.findAll(pageable)).thenReturn(bookPage);
        when(bookMapper.toResponseDto(book)).thenReturn(responseDto);

        // Act
        Page<BookResponseDto> result = bookService.getAllBooks(pageable);

        // Assert
        assertEquals(1, result.getTotalElements());
        assertEquals("Clean Code", result.getContent().get(0).getTitle());

        verify(bookRepository).findAll(pageable);
        verify(bookMapper).toResponseDto(book);
    }

    @Test
    void getBookById_shouldReturnBookSuccessfully() {

        // Arrange
        Long id = 1L;

        Book book = new Book();
        book.setId(id);
        book.setTitle("Clean Code");

        BookResponseDto responseDto = new BookResponseDto();
        responseDto.setId(id);
        responseDto.setTitle("Clean Code");

        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        when(bookMapper.toResponseDto(book)).thenReturn(responseDto);

        // Act
        BookResponseDto result = bookService.getBookById(id);

        // Assert
        assertEquals(id, result.getId());
        assertEquals("Clean Code", result.getTitle());

        verify(bookRepository).findById(id);
        verify(bookMapper).toResponseDto(book);
    }

    @Test
    void getBookById_shouldThrowExceptionWhenBookNotFound() {

        // Arrange
        Long id = 999L;

        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        BookNotFoundException exception = assertThrows(
                BookNotFoundException.class,
                () -> bookService.getBookById(id)
        );

        assertEquals("Book not found with ID : " + id, exception.getMessage());

        verify(bookRepository).findById(id);
        verify(bookMapper, never()).toResponseDto(any());
    }

    @Test
    void updateBook_shouldUpdateBookSuccessfully() {

        // Arrange
        Long id = 1L;

        BookRequestDto request = new BookRequestDto();
        request.setTitle("Clean Code Updated");
        request.setAuthor("Robert Martin");
        request.setIsbn("9780132350884");
        request.setPublishedYear(2008);
        request.setTotalCopies(7);

        Book book = new Book();
        book.setId(id);
        book.setTitle("Clean Code");
        book.setAuthor("Robert Martin");
        book.setIsbn("9780132350884");
        book.setPublishedYear(2008);
        book.setTotalCopies(5);
        book.setAvailableCopies(3);

        BookResponseDto response = new BookResponseDto();
        response.setId(id);
        response.setTitle("Clean Code Updated");
        response.setTotalCopies(7);
        response.setAvailableCopies(5);

        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.toResponseDto(book)).thenReturn(response);

        // Act
        BookResponseDto result = bookService.updateBook(id, request);

        // Assert
        assertEquals("Clean Code Updated", result.getTitle());
        assertEquals(7, result.getTotalCopies());
        assertEquals(5, result.getAvailableCopies());

        verify(bookRepository).findById(id);
        verify(bookRepository).save(book);
        verify(bookMapper).toResponseDto(book);
    }

    @Test
    void updateBook_shouldThrowExceptionWhenTotalCopiesLessThanBorrowed() {

        // Arrange
        Long id = 1L;

        BookRequestDto request = new BookRequestDto();
        request.setTotalCopies(1);

        Book book = new Book();
        book.setId(id);
        book.setTotalCopies(5);
        book.setAvailableCopies(3);

        when(bookRepository.findById(id)).thenReturn(Optional.of(book));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> bookService.updateBook(id, request)
        );

        assertEquals(
                "Total copies cannot be less than borrowed copies",
                exception.getMessage()
        );

        verify(bookRepository).findById(id);
        verify(bookRepository, never()).save(any());
        verify(bookMapper, never()).toResponseDto(any());
    }

    @Test
    void updateBook_shouldThrowExceptionWhenBookNotFound() {

        // Arrange
        Long id = 999L;

        BookRequestDto request = new BookRequestDto();
        request.setTotalCopies(5);

        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        BookNotFoundException exception = assertThrows(
                BookNotFoundException.class,
                () -> bookService.updateBook(id, request)
        );

        assertEquals(
                "Book not found with ID : " + id,
                exception.getMessage()
        );

        verify(bookRepository).findById(id);
        verify(bookRepository, never()).save(any());
        verify(bookMapper, never()).toResponseDto(any());
    }

    @Test
    void deleteBook_shouldDeleteBookSuccessfully() {

        // Arrange
        Long id = 1L;

        Book book = new Book();
        book.setId(id);
        book.setTotalCopies(5);
        book.setAvailableCopies(5);

        when(bookRepository.findById(id)).thenReturn(Optional.of(book));

        // Act
        bookService.deleteBook(id);

        // Assert
        verify(bookRepository).findById(id);
        verify(bookRepository).delete(book);
    }

    @Test
    void deleteBook_shouldThrowExceptionWhenCopiesAreBorrowed() {

        // Arrange
        Long id = 1L;

        Book book = new Book();
        book.setId(id);
        book.setTotalCopies(5);
        book.setAvailableCopies(3);

        when(bookRepository.findById(id)).thenReturn(Optional.of(book));

        // Act & Assert
        BookDeletionException exception = assertThrows(
                BookDeletionException.class,
                () -> bookService.deleteBook(id)
        );

        assertEquals(
                "Cannot delete book because copies are currently borrowed",
                exception.getMessage()
        );

        verify(bookRepository).findById(id);
        verify(bookRepository, never()).delete(any());
    }

    @Test
    void deleteBook_shouldThrowExceptionWhenBookNotFound() {

        // Arrange
        Long id = 999L;

        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        BookNotFoundException exception = assertThrows(
                BookNotFoundException.class,
                () -> bookService.deleteBook(id)
        );

        assertEquals(
                "Book not found with ID : " + id,
                exception.getMessage()
        );

        verify(bookRepository).findById(id);
        verify(bookRepository, never()).delete(any());
    }

    @Test
    void searchBook_shouldReturnMatchingBooks() {

        // Arrange
        String query = "clean";
        Pageable pageable = PageRequest.of(0, 10);

        Book book = new Book();
        book.setId(1L);
        book.setTitle("Clean Code");

        BookResponseDto responseDto = new BookResponseDto();
        responseDto.setId(1L);
        responseDto.setTitle("Clean Code");

        Page<Book> bookPage = new PageImpl<>(List.of(book));

        when(bookRepository
                .findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrIsbnContainingIgnoreCase(
                        query, query, query, pageable))
                .thenReturn(bookPage);

        when(bookMapper.toResponseDto(book)).thenReturn(responseDto);

        // Act
        Page<BookResponseDto> result = bookService.searchBook(query, pageable);

        // Assert
        assertEquals(1, result.getTotalElements());
        assertEquals("Clean Code", result.getContent().get(0).getTitle());

        verify(bookRepository)
                .findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrIsbnContainingIgnoreCase(
                        query, query, query, pageable);

        verify(bookMapper).toResponseDto(book);
    }

    @Test
    void searchBook_shouldReturnEmptyPageWhenNoBooksFound() {

        // Arrange
        String query = "xyz";
        Pageable pageable = PageRequest.of(0, 10);

        Page<Book> emptyPage = Page.empty();

        when(bookRepository
                .findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrIsbnContainingIgnoreCase(
                        query, query, query, pageable))
                .thenReturn(emptyPage);

        // Act
        Page<BookResponseDto> result = bookService.searchBook(query, pageable);

        // Assert
        assertEquals(0, result.getTotalElements());
        assertTrue(result.getContent().isEmpty());

        verify(bookRepository)
                .findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrIsbnContainingIgnoreCase(
                        query, query, query, pageable);

        verify(bookMapper, never()).toResponseDto(any());
    }
}
