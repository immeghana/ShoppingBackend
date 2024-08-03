package com.ShoppingWebsiteBackend.ShoppingWebsiteBackend.exception;
public class InvalidProductID extends RuntimeException{
    public InvalidProductID(String message){
        super(message);
    }
}
