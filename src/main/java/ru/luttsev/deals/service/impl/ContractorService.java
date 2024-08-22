package ru.luttsev.deals.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.luttsev.deals.model.payload.rabbitmq.MainBorrowerRabbitMessage;

/**
 * Сервис для работы с внешним сервисом контрагентов
 *
 * @author Yuri Luttsev
 */
@Service
@RequiredArgsConstructor
public class ContractorService {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.main-borrower-exchange}")
    private String mainBorrowerExchange;

    @Value("${rabbitmq.main-borrower-queue}")
    private String mainBorrowerQueue;

    /**
     * Отправка параметра mainBorrower во внешний сервис контрагентов
     *
     * @param contractorId ID контрагента
     * @param isMain       значение mainBorrower
     * @return true если запрос успешно отправлен, false в случае ошибки
     */
    public boolean sendMainBorrower(String contractorId, boolean isMain) {
        try {
            rabbitTemplate.convertAndSend(mainBorrowerExchange,
                    mainBorrowerQueue,
                    MainBorrowerRabbitMessage.builder()
                            .contractorId(contractorId)
                            .isMain(isMain)
                            .build());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
