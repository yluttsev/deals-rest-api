package ru.luttsev.deals.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.luttsev.deals.model.MessageAction;
import ru.luttsev.deals.model.MessageStatus;
import ru.luttsev.deals.model.entity.Deal;
import ru.luttsev.deals.model.entity.DealStatus;
import ru.luttsev.deals.model.entity.Outbox;
import ru.luttsev.deals.repository.OutboxRepository;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OutboxServiceImplTests {

    @Mock
    private OutboxRepository outboxRepository;

    @Mock
    private DealServiceImpl dealService;

    @InjectMocks
    private OutboxServiceImpl outboxService;

    @Test
    @DisplayName("Повторная отправка сообщения без актуального статуса")
    void testResendFailureMessageWithoutActualStatus() {
        Outbox failureMessage = Outbox.builder()
                .contractorId("test_id")
                .status(MessageStatus.ERROR)
                .isMain(true)
                .action(MessageAction.CHANGE_STATUS_ACTIVE)
                .build();
        Deal deal = Deal.builder()
                .status(
                        DealStatus.builder()
                                .id("CLOSED")
                                .name("Закрытая")
                                .build()
                ).build();
        when(outboxRepository.findErrorMessages()).thenReturn(Collections.singletonList(failureMessage));
        when(dealService.getDealByContractorId("test_id")).thenReturn(deal);
        outboxService.resendMessage();
        assertEquals(MessageStatus.SUCCESS, failureMessage.getStatus());
    }

}
