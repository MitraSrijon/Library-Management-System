package com.srijon.library.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BookRequestDto {

    @NotBlank(message = "Title of book cannot be blank")
    private String title;

    @NotBlank(message = "The Author Name of the book must be mentioned")
    private String author;

    @NotBlank(message = "ISBN number cannot be blank or null")
    private String isbn;

    @NotNull(message = "Publication year must be mentioned")
    private Integer publishedYear;

    @Min(1)
    private Integer totalCopies;

}
