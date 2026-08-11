package com.srijon.library.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookResponseDto {

    private Long id;
    private String title;
    private String author;
    private String isbn;
    private Integer publishedYear;
    private Integer totalCopies;
    private Integer availableCopies;

}
