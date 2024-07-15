package ru.luttsev.deals.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.luttsev.deals.exception.DealNotFoundException;
import ru.luttsev.deals.model.entity.Deal;
import ru.luttsev.deals.repository.DealRepository;
import ru.luttsev.deals.service.CrudService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DealServiceImpl implements CrudService<Deal, UUID> {

    private final DealRepository dealRepository;

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
    public void deleteById(UUID id) {
        dealRepository.deleteById(id);
    }

}
