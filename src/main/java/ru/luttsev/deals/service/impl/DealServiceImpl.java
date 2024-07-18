package ru.luttsev.deals.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.luttsev.deals.exception.DealNotFoundException;
import ru.luttsev.deals.model.entity.Deal;
import ru.luttsev.deals.model.entity.DealStatus;
import ru.luttsev.deals.model.payload.deal.DealFiltersPayload;
import ru.luttsev.deals.model.payload.deal.DealPagePayload;
import ru.luttsev.deals.model.payload.deal.DealPayload;
import ru.luttsev.deals.model.payload.dealcontractor.DealSpecification;
import ru.luttsev.deals.repository.DealRepository;
import ru.luttsev.deals.service.DealService;
import ru.luttsev.deals.service.DealStatusService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DealServiceImpl implements DealService {

    private final DealRepository dealRepository;

    private final DealStatusService dealStatusService;

    private final ModelMapper mapper;

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
    public Deal save(Deal entity) {
        return dealRepository.save(entity);
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        Deal deal = this.getById(id);
        deal.setActive(false);
        this.save(deal);
    }

    @Override
    @Transactional
    public Deal updateStatus(String dealId, String statusId) {
        DealStatus status = dealStatusService.getById(dealId);
        Deal deal = this.getById(UUID.fromString(statusId));
        deal.setStatus(status);
        return this.save(deal);
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

}
