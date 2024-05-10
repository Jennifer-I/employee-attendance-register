package com.jennifer.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class UserAlreadyExistException extends RuntimeException {
    private String message;
    private String errorCode;
    private String status;

    public UserAlreadyExistException(String message) {
        super(message);

    }
}
