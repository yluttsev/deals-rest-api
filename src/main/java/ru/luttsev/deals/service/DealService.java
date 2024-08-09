package ru.luttsev.deals.service;

import org.springframework.security.core.userdetails.UserDetails;
import ru.luttsev.deals.model.entity.Deal;
import ru.luttsev.deals.model.entity.DealContractor;
import ru.luttsev.deals.model.payload.deal.DealFiltersPayload;
import ru.luttsev.deals.model.payload.deal.DealPagePayload;

import java.util.Optional;
import java.util.UUID;

/**
 * Сервис для работы со сделками
 *
 * @author Yuri Luttsev
 */
public interface DealService extends CrudService<Deal, UUID> {

    DealPagePayload getByFilters(DealFiltersPayload filters, int page, int contentSize);

    DealPagePayload getByFiltersWithCheckRole(DealFiltersPayload filters, int page, int contentSize, UserDetails userDetails);

    int numberOfActiveDeals(String contractorId);

    Optional<DealContractor> getMainContractorByDealId(UUID dealId);

}
