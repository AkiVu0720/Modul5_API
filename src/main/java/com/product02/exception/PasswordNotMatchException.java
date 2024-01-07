package com.product02.exception;

import lombok.Data;

@Data
public class PasswordNotMatchException extends  RuntimeException{
    private String message;
    public PasswordNotMatchException(String message){
        this.message = message;
    }
}
