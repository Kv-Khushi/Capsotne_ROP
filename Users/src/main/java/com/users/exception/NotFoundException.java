package com.users.exception;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String userEmail){
        super ("User Not Found");
    }
}
