package com.srijon.library.exception;

import com.srijon.library.dto.BookResponseDto;

public class BookDeletionException extends RuntimeException{

    public BookDeletionException(String message){
        super(message);
    }
}
