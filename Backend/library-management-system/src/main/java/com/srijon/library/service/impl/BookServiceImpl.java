package com.srijon.library.service.impl;

import com.srijon.library.dto.BookRequestDto;
import com.srijon.library.dto.BookResponseDto;
import com.srijon.library.entity.Book;
import com.srijon.library.exception.BookDeletionException;
import com.srijon.library.exception.BookNotFoundException;
import com.srijon.library.mapper.BookMapper;
import com.srijon.library.repository.BookRepository;
import com.srijon.library.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    //Searching a book with its specific ID
    @Override
    public BookResponseDto getBookById(Long id) {

        Book book = bookRepository.findById(id)
                .orElseThrow(
                        () -> new BookNotFoundException("Book not found with ID : " + id)
                );

        return mapper.toResponseDto(book);
    }

    //Logic of updating a book
    @Override
    public BookResponseDto updateBook(Long id, BookRequestDto bookRequestDto) {

        Book book = bookRepository.findById(id)
                .orElseThrow(
                        () -> new BookNotFoundException("Book not found with ID : " + id)
                );

        //Checking the borrowed copies
        int borrowedCopies = book.getTotalCopies() - book.getAvailableCopies();

        if (bookRequestDto.getTotalCopies() < borrowedCopies) {
            throw new IllegalArgumentException(
                    "Total copies cannot be less than borrowed copies"
            );
        }

        //New available copies
        int newAvailableCopies = bookRequestDto.getTotalCopies() - borrowedCopies;

        //Updating all the values
        book.setAuthor(bookRequestDto.getAuthor());
        book.setTitle(bookRequestDto.getTitle());
        book.setIsbn(bookRequestDto.getIsbn());
        book.setTotalCopies(bookRequestDto.getTotalCopies());
        book.setPublishedYear(bookRequestDto.getPublishedYear());
        book.setAvailableCopies(newAvailableCopies);

        Book updateBook = bookRepository.save(book);

        return mapper.toResponseDto(updateBook);
    }

    //Logic of deleting a book
    @Override
    public void deleteBook(Long id) {

        Book deleteBook = bookRepository.findById(id).orElseThrow(
                () -> new BookNotFoundException("Book not found with ID : " + id)
        );

        int borrowedCopies = deleteBook.getTotalCopies() - deleteBook.getAvailableCopies();

        if(borrowedCopies > 0) {
            throw new BookDeletionException(
                    "Cannot delete book because copies are currently borrowed"
            );
        }

        bookRepository.delete(deleteBook);

    }
}
