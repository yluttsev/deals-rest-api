package ru.luttsev.deals.model.payload.rabbitmq;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ContractorRabbitMessage {

    private ContractorRabbitPayload contractor;

    private final LocalDateTime modifyDate;

}
