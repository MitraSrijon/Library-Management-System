package com.srijon.library.exception;

public class LoanAlreadyReturnedException extends RuntimeException{

    public LoanAlreadyReturnedException(String message){
        super(message);
    }
}
