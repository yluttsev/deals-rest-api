package ru.luttsev.deals.service;

import ru.luttsev.deals.model.entity.DealContractor;
import ru.luttsev.deals.model.payload.rabbitmq.ContractorRabbitPayload;

import java.util.List;
import java.util.UUID;

/**
 * Сервис для работы с контрагентами сделки
 *
 * @author Yuri Luttsev
 */
public interface DealContractorService extends CrudService<DealContractor, UUID> {

    List<DealContractor> getByContractorId(String contractorId);

    void updateByRabbitMessage(ContractorRabbitPayload payload, DealContractor dealContractor);

}
