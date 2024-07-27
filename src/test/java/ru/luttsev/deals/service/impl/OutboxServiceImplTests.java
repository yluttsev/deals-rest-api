package ru.luttsev.deals.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.luttsev.deals.model.MessageStatus;
import ru.luttsev.deals.model.entity.Outbox;
import ru.luttsev.deals.repository.OutboxRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OutboxServiceImplTests {

    @Mock
    private OutboxRepository outboxRepository;

    @Mock
    private ContractorService contractorService;

    @InjectMocks
    private OutboxServiceImpl outboxService;

    @Test
    @DisplayName("Отправка сообщений со статусом CREATED")
    void testSendCreatedMessages() {
        Outbox createdMessage1 = Outbox.builder()
                .contractorId("test_id")
                .isMain(true)
                .status(MessageStatus.CREATED)
                .build();
        Outbox createdMessage2 = Outbox.builder()
                .contractorId("test_id2")
                .isMain(false)
                .status(MessageStatus.CREATED)
                .build();
        when(outboxRepository.findByStatus(MessageStatus.CREATED)).thenReturn(List.of(createdMessage1, createdMessage2));
        when(contractorService.sendMainBorrower(createdMessage1.getContractorId(), createdMessage1.isMain())).thenReturn(true);
        when(contractorService.sendMainBorrower(createdMessage2.getContractorId(), createdMessage2.isMain())).thenReturn(false);

        outboxService.resendMessage();

        assertAll(
                () -> assertEquals(createdMessage1.getStatus(), MessageStatus.SUCCESS),
                () -> assertEquals(createdMessage2.getStatus(), MessageStatus.CREATED)
        );
    }

}
