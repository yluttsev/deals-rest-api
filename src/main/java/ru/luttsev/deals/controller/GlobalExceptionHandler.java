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

/**
 * Глобальный обрабочтик исключений
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Обработчик {@link ContractorRoleNotFoundException ContractorRoleNotFoundException} исключения
     *
     * @param e {@link ContractorRoleNotFoundException пользовательское исключение}
     * @return {@link ProblemDetail информация об ошибке}
     */
    @ExceptionHandler(ContractorRoleNotFoundException.class)
    public ProblemDetail contractorRoleNotFound(ContractorRoleNotFoundException e) {
        return createProblemDetail(e.getMessage());
    }

    /**
     * Обработчик {@link DealContractorNotFoundException DealContractorNotFoundException} исключения
     *
     * @param e {@link DealContractorNotFoundException пользовательское исключение}
     * @return {@link ProblemDetail информация об ошибке}
     */
    @ExceptionHandler(DealContractorNotFoundException.class)
    public ProblemDetail dealContractorNotFound(DealContractorNotFoundException e) {
        return createProblemDetail(e.getMessage());
    }

    /**
     * Обработчик {@link DealNotFoundException DealNotFoundException} исключения
     *
     * @param e {@link DealNotFoundException пользовательское исключение}
     * @return {@link ProblemDetail информация об ошибке}
     */
    @ExceptionHandler(DealNotFoundException.class)
    public ProblemDetail dealNotFound(DealNotFoundException e) {
        return createProblemDetail(e.getMessage());
    }

    /**
     * Обработчик {@link DealStatusNotFoundException DealStatusNotFoundException} исключения
     *
     * @param e {@link DealStatusNotFoundException пользовательское исключение}
     * @return {@link ProblemDetail информация об ошибке}
     */
    @ExceptionHandler(DealStatusNotFoundException.class)
    public ProblemDetail dealStatusNotFound(DealStatusNotFoundException e) {
        return createProblemDetail(e.getMessage());
    }

    /**
     * Обработчик {@link DealTypeNotFoundException DealTypeNotFoundException} исключения
     *
     * @param e {@link DealTypeNotFoundException пользовательское исключение}
     * @return {@link ProblemDetail информация об ошибке}
     */
    @ExceptionHandler(DealTypeNotFoundException.class)
    public ProblemDetail dealTypeNotFound(DealTypeNotFoundException e) {
        return createProblemDetail(e.getMessage());
    }

    /**
     * Создает {@link ProblemDetail информацию об ошибке}
     *
     * @param message сообщение исключения
     * @return {@link ProblemDetail информацию об ошибке}
     */
    private ProblemDetail createProblemDetail(String message) {
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                message
        );
    }

}
