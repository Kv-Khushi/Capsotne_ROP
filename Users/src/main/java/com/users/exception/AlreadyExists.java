package com.users.exception;

public class AlreadyExists extends RuntimeException{
 public AlreadyExists(String message){
  super(message);
 }
}
