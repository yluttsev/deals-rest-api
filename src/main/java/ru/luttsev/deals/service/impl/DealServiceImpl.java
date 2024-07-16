package ru.luttsev.deals.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.luttsev.deals.exception.DealNotFoundException;
import ru.luttsev.deals.model.entity.Deal;
import ru.luttsev.deals.model.entity.DealStatus;
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

}
