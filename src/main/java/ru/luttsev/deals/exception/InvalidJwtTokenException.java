package ru.luttsev.deals.exception;

public class InvalidJwtTokenException extends RuntimeException {

    public InvalidJwtTokenException() {
        super("Invalid JWT token.");
    }
}
