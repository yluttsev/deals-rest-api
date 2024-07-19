package ru.luttsev.deals.service;

import ru.luttsev.deals.model.entity.Deal;
import ru.luttsev.deals.model.payload.deal.DealFiltersPayload;
import ru.luttsev.deals.model.payload.deal.DealPagePayload;

import java.util.UUID;

public interface DealService extends CrudService<Deal, UUID> {

    DealPagePayload getByFilters(DealFiltersPayload filters, int page, int contentSize);

}
