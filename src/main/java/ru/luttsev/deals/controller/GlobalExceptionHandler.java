package ru.luttsev.deals.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.luttsev.deals.exception.ContractorRoleNotFoundException;
import ru.luttsev.deals.exception.DealContractorNotFoundException;
import ru.luttsev.deals.exception.DealNotFoundException;
import ru.luttsev.deals.exception.DealStatusNotFoundException;
import ru.luttsev.deals.exception.DealTypeNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ContractorRoleNotFoundException.class)
    public ProblemDetail contractorRoleNotFound(ContractorRoleNotFoundException e) {
        return createProblemDetail(e.getMessage());
    }

    @ExceptionHandler(DealContractorNotFoundException.class)
    public ProblemDetail dealContractorNotFound(DealContractorNotFoundException e) {
        return createProblemDetail(e.getMessage());
    }

    @ExceptionHandler(DealNotFoundException.class)
    public ProblemDetail dealNotFound(DealNotFoundException e) {
        return createProblemDetail(e.getMessage());
    }

    @ExceptionHandler(DealStatusNotFoundException.class)
    public ProblemDetail dealStatusNotFound(DealStatusNotFoundException e) {
        return createProblemDetail(e.getMessage());
    }

    @ExceptionHandler(DealTypeNotFoundException.class)
    public ProblemDetail dealTypeNotFound(DealTypeNotFoundException e) {
        return createProblemDetail(e.getMessage());
    }

    private ProblemDetail createProblemDetail(String message) {
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                message
        );
    }

}
