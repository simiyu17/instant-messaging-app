package com.chat.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("User not found!!");
    }

    public UserNotFoundException(Long id) {
        super(String.format("User with ID  %d not found!!", id));
    }

    public UserNotFoundException(String s) {
        super(s);
    }
}
