package ru.luttsev.deals.service;

import ru.luttsev.deals.model.entity.Outbox;

/**
 * Сервис для работы с outbox таблицей
 *
 * @author Yuri Luttsev
 */
public interface OutboxService {

    /**
     * Сохранение сообщения в таблице
     *
     * @param message сохраненое сообщение
     */
    void save(Outbox message);

    /**
     * Получение неудачно отправленных сообщений
     *
     * @return список неудачно отправленных сообщений
     */
    Outbox getLastOutboxMessage(String contractorId);

    void resendMessage();

}
