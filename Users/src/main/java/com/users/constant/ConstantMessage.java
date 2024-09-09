package com.users.constant;

/**
 * A utility class that holds constant messages and values used throughout the application.
 * <p>
 * This class is not intended to be instantiated.
 * </p>
 */
public final class ConstantMessage {

 /**
  * Private constructor to prevent instantiation.
  */
 private ConstantMessage() {
  // Prevent instantiation
 }

 /**
  * The message to be used when a user is not found.
  */
 public static final String NOT_FOUND = "User not found";

 /**
  * The message to be used when an email already exists in the system.
  */
 public static final String ALREADY_EXISTS = "Email already exists";

 /**
  * The default wallet amount used in the system.
  */
 public static final Long WALLET_AMOUNT = 1000L;


 public static final String USER_ADD_SUCCESS = "User added successfully";
}

