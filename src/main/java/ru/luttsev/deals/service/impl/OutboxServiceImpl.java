package ru.luttsev.deals.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.luttsev.deals.model.MessageStatus;
import ru.luttsev.deals.model.entity.Outbox;
import ru.luttsev.deals.repository.OutboxRepository;
import ru.luttsev.deals.service.OutboxService;

import java.util.List;

/**
 * Реализация сервиса для работы с outbox таблицей
 *
 * @author Yuri Luttsev
 */
@Service
@RequiredArgsConstructor
public class OutboxServiceImpl implements OutboxService {

    private final ContractorService contractorService;

    private final OutboxRepository outboxRepository;

    @Override
    public void save(Outbox message) {
        outboxRepository.save(message);
    }

    @Override
    public Outbox getLastOutboxMessage(String contractorId) {
        return outboxRepository.findByContractorId(contractorId).orElse(null);
    }

    @Override
    @Transactional
    public void resendMessage() {
        List<Outbox> outboxMessages = outboxRepository.findByStatus(MessageStatus.CREATED);
        outboxMessages.forEach(outboxMessage -> {
            boolean result = contractorService.sendMainBorrower(outboxMessage.getContractorId(), outboxMessage.isMain());
            if (result) {
                outboxMessage.setStatus(MessageStatus.SUCCESS);
            }
        });
    }

}
