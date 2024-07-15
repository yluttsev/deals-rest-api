package ru.luttsev.deals.exception;

public class ContractorRoleNotFoundException extends RuntimeException {

    public ContractorRoleNotFoundException(String id) {
        super("Contractor role with id: %s not found.".formatted(id));
    }
}
