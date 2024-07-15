package ru.luttsev.deals.exception;

public class DealStatusNotFoundException extends RuntimeException {

    public DealStatusNotFoundException(String dealStatusId) {
        super("Deal status with id: %s not found.".formatted(dealStatusId));
    }

}
