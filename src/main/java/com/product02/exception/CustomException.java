package com.product02.exception;

import lombok.Data;

@Data
public class CustomException extends  RuntimeException {
    private String message;
    public CustomException(String message){
        this.message = message;
    }
}
