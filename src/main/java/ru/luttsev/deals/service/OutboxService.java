package ru.luttsev.deals.service;

import ru.luttsev.deals.model.MessageStatus;
import ru.luttsev.deals.model.entity.Outbox;

import java.util.List;
import java.util.UUID;

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
    void updateMessageStatus(UUID messageId, MessageStatus messageStatus);

    void resendMessage();

}
