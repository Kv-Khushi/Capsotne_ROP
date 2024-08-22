package com.users.exception;

public class AlreadyExists extends RuntimeException{
 public AlreadyExists(){
  super ("Email Already Exists");
 }
}
