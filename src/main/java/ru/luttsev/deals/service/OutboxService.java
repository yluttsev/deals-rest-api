package ru.luttsev.deals.service;

import ru.luttsev.deals.model.entity.Outbox;

import java.util.List;

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
     * Получение последнего сообщения из таблицы
     *
     * @return последнее сообщение
     */
    List<Outbox> getErrorMessages();

    void updateMessageStatus(String messageId, String messageStatus);

}
