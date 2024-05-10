package com.jennifer.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserNotFoundException extends RuntimeException{
    private String message;
    private String errorCode;
    private String status;

    public UserNotFoundException(String message) {
        super(message);
    }
}
