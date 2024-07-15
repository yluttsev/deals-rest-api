package ru.luttsev.deals.exception;

import java.util.UUID;

public class DealNotFoundException extends RuntimeException {

    public DealNotFoundException(UUID id) {
        super("Deal with id: %s not found.".formatted(id.toString()));
    }
}
