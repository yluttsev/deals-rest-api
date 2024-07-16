package ru.luttsev.deals.service;

import ru.luttsev.deals.model.entity.Deal;

import java.util.UUID;

public interface DealService extends CrudService<Deal, UUID> {

    Deal updateStatus(String dealId, String statusId);

}
