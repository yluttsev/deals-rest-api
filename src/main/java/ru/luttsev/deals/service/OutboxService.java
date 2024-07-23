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
     * Получение неудачно отправленных сообщений
     *
     * @return список неудачно отправленных сообщений
     */
    List<Outbox> getErrorMessages();

    /**
     * Обновление статуса сообщения
     *
     * @param messageId     ID сообщения
     * @param messageStatus статус сообщения
     */
    void updateMessageStatus(String messageId, String messageStatus);

}
