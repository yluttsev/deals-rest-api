package ru.luttsev.deals.exception;

import java.util.UUID;

public class DealContractorNotFoundException extends RuntimeException {

    public DealContractorNotFoundException(UUID id) {
        super("Deal contractor with id: %s not found.".formatted(id.toString()));
    }
}
