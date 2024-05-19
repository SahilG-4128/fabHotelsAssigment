package com.example.skillbasedendorsementapplication.exceptionhandling;

public class CustomException extends RuntimeException{
    public CustomException(String message) {
        super(message);
    }
    public CustomException(String message,Throwable reason) {
        super(message,reason);
    }
}
