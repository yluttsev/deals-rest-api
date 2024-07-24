package ru.luttsev.deals.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.luttsev.deals.model.MessageAction;
import ru.luttsev.deals.model.MessageStatus;
import ru.luttsev.deals.model.entity.Deal;
import ru.luttsev.deals.model.entity.Outbox;
import ru.luttsev.deals.repository.OutboxRepository;
import ru.luttsev.deals.service.DealService;
import ru.luttsev.deals.service.OutboxService;

import java.util.List;
import java.util.UUID;

/**
 * Реализация сервиса для работы с outbox таблицей
 *
 * @author Yuri Luttsev
 */
@Service
@RequiredArgsConstructor
public class OutboxServiceImpl implements OutboxService {

    private final OutboxRepository outboxRepository;

    private final ContractorService contractorService;

    private final DealService dealService;

    @Override
    public void save(Outbox message) {
        outboxRepository.save(message);
    }

    @Override
    public List<Outbox> getErrorMessages() {
        return outboxRepository.findErrorMessages();
    }

    @Override
    public void updateMessageStatus(UUID messageId, MessageStatus messageStatus) {
        outboxRepository.updateMessageStatus(messageId, messageStatus);
    }

    @Override
    public void resendMessage() {
        List<Outbox> errorMessages = this.getErrorMessages();
        errorMessages.forEach(message -> {
            if (isResend(message)) {
                boolean result = contractorService.sendMainBorrower(message.getContractorId(), message.isMain());
                if (result) {
                    message.setStatus(MessageStatus.SUCCESS);
                }
            }
        });
    }

    private boolean isResend(Outbox outbox) {
        Deal deal = dealService.getDealByContractorId(outbox.getContractorId());
        if (deal.getStatus().getId().equals("CLOSED") && outbox.getAction().equals(MessageAction.CHANGE_STATUS_ACTIVE)) {
            outbox.setStatus(MessageStatus.SUCCESS);
            this.save(outbox);
            return false;
        }
        return true;
    }

}
