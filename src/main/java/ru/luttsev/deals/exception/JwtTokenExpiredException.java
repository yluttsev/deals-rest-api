package ru.luttsev.deals.exception;

public class JwtTokenExpiredException extends RuntimeException {

    public JwtTokenExpiredException() {
        super("JWT token has expired.");
    }
}
