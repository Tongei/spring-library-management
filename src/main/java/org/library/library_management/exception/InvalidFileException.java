package org.library.library_management.exception;

import org.springframework.http.HttpStatus;

public class InvalidFileException extends ApiException{

    public InvalidFileException(String message) {
        super(HttpStatus.BAD_REQUEST, String.format("Invalid input! %s", message));
    }
}
