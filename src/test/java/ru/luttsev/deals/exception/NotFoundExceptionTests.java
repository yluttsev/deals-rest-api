package ru.luttsev.deals.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.luttsev.deals.service.ContractorRoleService;
import ru.luttsev.deals.service.DealContractorService;
import ru.luttsev.deals.service.DealService;
import ru.luttsev.deals.service.DealStatusService;
import ru.luttsev.deals.service.DealTypeService;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotFoundExceptionTests {

    @Mock
    private ContractorRoleService contractorRoleService;

    @Mock
    private DealContractorService dealContractorService;

    @Mock
    private DealService dealService;

    @Mock
    private DealStatusService dealStatusService;

    @Mock
    private DealTypeService dealTypeService;

    private static final UUID INVALID_ID = UUID.randomUUID();

    @Test
    @DisplayName("Тест выброса исключения ContractorRoleNotFoundException")
    void testThrowingContractorRoleNotFoundException() {
        when(contractorRoleService.getById(INVALID_ID.toString())).thenThrow(
                new ContractorRoleNotFoundException(INVALID_ID.toString())
        );

        ContractorRoleNotFoundException exception = assertThrows(
                ContractorRoleNotFoundException.class,
                () -> contractorRoleService.getById(INVALID_ID.toString())
        );
        assertEquals(exception.getMessage(), "Contractor role with id: %s not found.".formatted(INVALID_ID));
    }

    @Test
    @DisplayName("Тест выброса исключения DealContractorNotFoundException")
    void testThrowingDealContractorNotFoundException() {
        when(dealContractorService.getById(INVALID_ID)).thenThrow(
                new DealContractorNotFoundException(INVALID_ID)
        );

        DealContractorNotFoundException exception = assertThrows(
                DealContractorNotFoundException.class,
                () -> dealContractorService.getById(INVALID_ID)
        );
        assertEquals(exception.getMessage(), "Deal contractor with id: %s not found.".formatted(INVALID_ID.toString()));
    }

    @Test
    @DisplayName("Тест выброса исключения DealNotFoundException")
    void testThrowingDealNotFoundException() {
        when(dealService.getById(INVALID_ID)).thenThrow(
                new DealNotFoundException(INVALID_ID)
        );

        DealNotFoundException exception = assertThrows(
                DealNotFoundException.class,
                () -> dealService.getById(INVALID_ID)
        );
        assertEquals(exception.getMessage(), "Deal with id: %s not found.".formatted(INVALID_ID.toString()));
    }

    @Test
    @DisplayName("Тест выброса исключения DealStatusNotFoundException")
    void testThrowingDealStatusNotFoundException() {
        when((dealStatusService.getById(INVALID_ID.toString()))).thenThrow(
                new DealStatusNotFoundException(INVALID_ID.toString())
        );

        DealStatusNotFoundException exception = assertThrows(
                DealStatusNotFoundException.class,
                () -> dealStatusService.getById(INVALID_ID.toString())
        );
        assertEquals(exception.getMessage(), "Deal status with id: %s not found.".formatted(INVALID_ID.toString()));
    }

    @Test
    @DisplayName("Тест выброса исключения DealTypeNotFoundException")
    void testThrowingDealTypeNotFoundException() {
        when(dealTypeService.getById(INVALID_ID.toString())).thenThrow(
                new DealTypeNotFoundException(INVALID_ID.toString())
        );

        DealTypeNotFoundException exception = assertThrows(
                DealTypeNotFoundException.class,
                () -> dealTypeService.getById(INVALID_ID.toString())
        );
        assertEquals(exception.getMessage(), "Deal type with id: %s not found.".formatted(INVALID_ID.toString()));
    }

}
