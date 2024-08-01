package ru.luttsev.deals.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.luttsev.deals.exception.DealNotFoundException;
import ru.luttsev.deals.model.entity.Deal;
import ru.luttsev.deals.model.entity.DealContractor;
import ru.luttsev.deals.model.payload.deal.DealFiltersPayload;
import ru.luttsev.deals.model.payload.deal.DealPagePayload;
import ru.luttsev.deals.model.payload.deal.DealPayload;
import ru.luttsev.deals.model.payload.dealcontractor.DealSpecification;
import ru.luttsev.deals.repository.DealRepository;
import ru.luttsev.deals.service.DealService;
import ru.luttsev.deals.service.SecurityService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Реализация сервиса для работы со сделками
 *
 * @author Yuri Luttsev
 */
@Service
@RequiredArgsConstructor
@PreAuthorize("!hasRole('ADMIN')")
public class DealServiceImpl implements DealService {

    private final DealRepository dealRepository;

    private final ModelMapper mapper;

    private final SecurityService securityService;

    @Override
    public List<Deal> getAll() {
        return dealRepository.findAll();
    }

    @Override
    public Deal getById(UUID id) {
        return dealRepository.findById(id).orElseThrow(
                () -> new DealNotFoundException(id)
        );
    }

    @Override
    @PreAuthorize("hasAnyRole('DEAL_SUPERUSER', 'SUPERUSER')")
    public Deal save(Deal entity) {
        return dealRepository.save(entity);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('DEAL_SUPERUSER', 'SUPERUSER')")
    public void deleteById(UUID id) {
        Deal deal = this.getById(id);
        deal.setActive(false);
        this.save(deal);
    }

    @Override
    public DealPagePayload getByFilters(DealFiltersPayload filters, int page, int contentSize) {
        Specification<Deal> specification = DealSpecification.getSpecification(filters);
        Page<Deal> searchDeals = dealRepository.findAll(specification, PageRequest.of(page, contentSize));
        return DealPagePayload.builder()
                .page(page)
                .items(searchDeals.getNumberOfElements())
                .deals(searchDeals.get().map(deal -> mapper.map(deal, DealPayload.class)).toList())
                .build();
    }

    @Override
    @PreAuthorize("hasAnyRole('DEAL_SUPERUSER', 'SUPERUSER', 'CREDIT_USER', 'OVERDRAFT_USER')")
    public DealPagePayload getByFiltersWithCheckRole(DealFiltersPayload filters, int page, int contentSize, UserDetails userDetails) {
        securityService.updateFiltersWithRole(filters, userDetails);
        return this.getByFilters(filters, page, contentSize);

    }

    @Override
    public int numberOfActiveDeals(String contractorId) {
        return dealRepository.numberOfActiveDealsByContractorId(contractorId);
    }

    @Override
    public Optional<DealContractor> getMainContractorByDealId(UUID dealId) {
        return dealRepository.findMainContractorByDealId(dealId);
    }

}
