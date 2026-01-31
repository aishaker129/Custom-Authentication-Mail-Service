package com.authentication.exception;

public class TokenAlreadyExpiredException extends RuntimeException{
    public TokenAlreadyExpiredException(String message){
        super(message);
    }
}
