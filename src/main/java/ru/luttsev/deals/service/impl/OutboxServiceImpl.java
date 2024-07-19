package ru.luttsev.deals.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.luttsev.deals.model.entity.Outbox;
import ru.luttsev.deals.repository.OutboxRepository;
import ru.luttsev.deals.service.OutboxService;

@Service
@RequiredArgsConstructor
public class OutboxServiceImpl implements OutboxService {

    private final OutboxRepository outboxRepository;

    @Override
    public void save(Outbox message) {
        outboxRepository.save(message);
    }

}
