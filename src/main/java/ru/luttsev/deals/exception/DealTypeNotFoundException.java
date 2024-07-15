package ru.luttsev.deals.exception;

public class DealTypeNotFoundException extends RuntimeException {

    public DealTypeNotFoundException(String id) {
        super("Deal type with id: %s not found.".formatted(id));
    }
}
