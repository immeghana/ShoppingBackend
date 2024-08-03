package com.ShoppingWebsiteBackend.ShoppingWebsiteBackend.exception;

public class UserNotFound extends RuntimeException{
    public UserNotFound(String message){
        super(message);
    }
}

