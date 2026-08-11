package com.srijon.library.mapper;

import com.srijon.library.dto.BookRequestDto;
import com.srijon.library.dto.BookResponseDto;
import com.srijon.library.entity.Book;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public Book toEntity(BookRequestDto dto){

        Book book = new Book();

        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setIsbn(dto.getIsbn());
        book.setPublishedYear(dto.getPublishedYear());
        book.setTotalCopies(dto.getTotalCopies());

        return book;
    }

    public BookResponseDto toResponseDto(Book book){

        BookResponseDto bookResponseDto = new BookResponseDto();

        bookResponseDto.setId(book.getId());
        bookResponseDto.setTitle(book.getTitle());
        bookResponseDto.setAuthor(book.getAuthor());
        bookResponseDto.setIsbn(book.getIsbn());
        bookResponseDto.setPublishedYear(book.getPublishedYear());
        bookResponseDto.setTotalCopies(book.getTotalCopies());
        bookResponseDto.setAvailableCopies(book.getAvailableCopies());

        return bookResponseDto;
    }

}
