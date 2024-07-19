package ru.luttsev.deals.service;

import ru.luttsev.deals.model.entity.Outbox;

public interface OutboxService {

    void save(Outbox message);

}
