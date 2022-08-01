package com.ensim.muremagique.services;

public class NotFoundException extends RuntimeException{

    public NotFoundException(String message)
    {
        super(message);
    }
}
