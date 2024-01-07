package com.product02.exception;
/**
 * Quan lys Exception tap trung
 */

import com.product02.payload.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalCustomException {
    @ExceptionHandler(UserNotFoundException.class) //dang ki exception
    public ResponseEntity<?> handleUserNotFoundException(Exception e){
        BaseResponse response = new BaseResponse();
        response.setStatusCode(401);
        response.setData(e.getMessage());
        return  new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(PasswordNotMatchException.class)
    public ResponseEntity<?>handlePassWordNotMatch(Exception e){
        BaseResponse response = new BaseResponse();
        response.setStatusCode(401);
        response.setData(e.getMessage());
        return  new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?>handleAddressNotMatch(Exception e){
        BaseResponse response = new BaseResponse();
        response.setStatusCode(401);
        response.setData(e.getMessage());
        return  new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }
}
