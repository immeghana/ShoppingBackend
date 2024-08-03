package com.ShoppingWebsiteBackend.ShoppingWebsiteBackend.exception;

public class WrongCredentials extends RuntimeException{
    public WrongCredentials(String message){
        super(message);
    }
}
