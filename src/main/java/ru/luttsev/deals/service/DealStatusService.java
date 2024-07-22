package ru.luttsev.deals.service;

import ru.luttsev.deals.model.entity.Deal;
import ru.luttsev.deals.model.entity.DealStatus;

/**
 * Сервис для работы со статусами сделки
 *
 * @author Yuri Luttsev
 */
public interface DealStatusService extends CrudService<DealStatus, String> {

    Deal updateStatus(String dealId, String statusId);

}
