package com.example.spaceprobeapi.exception;

public class InvalidPositionException extends RuntimeException {

    public InvalidPositionException(String message) {
        super(message);
    }
}
