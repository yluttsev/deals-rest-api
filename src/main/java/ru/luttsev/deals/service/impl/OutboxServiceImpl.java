package ru.luttsev.deals.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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

    private final OutboxRepository outboxRepository;

    @Override
    public void save(Outbox message) {
        outboxRepository.save(message);
    }

    @Override
    public List<Outbox> getErrorMessages() {
        return outboxRepository.findErrorMessages();
    }

    @Override
    public void updateMessageStatus(String messageId, String messageStatus) {
        outboxRepository.updateMessageStatus(messageId, messageStatus);
    }

}
