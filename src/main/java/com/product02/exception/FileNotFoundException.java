package com.product02.exception;

public class FileNotFoundException extends RuntimeException{
    private String message;
    public FileNotFoundException(String message){
        this.message = message;
    }
}
