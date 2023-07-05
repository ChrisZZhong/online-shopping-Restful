package com.shop.onlineshopping.AOP;

import com.shop.onlineshopping.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ErrorResponse> handleException(Exception e){
        return new ResponseEntity<>(ErrorResponse.builder().status("failed").message(e.getMessage()).build(), HttpStatus.OK);
    }

    @ExceptionHandler(value = {BadCredentialsException.class})
    public ResponseEntity<ErrorResponse> handleBadLoginRequestException(BadCredentialsException e){
        return new ResponseEntity<>(ErrorResponse.builder().message(e.getMessage()).build(), HttpStatus.OK);
    }

}
