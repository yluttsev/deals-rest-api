package ru.luttsev.deals.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.luttsev.deals.exception.DealStatusNotFoundException;
import ru.luttsev.deals.model.entity.DealStatus;
import ru.luttsev.deals.repository.DealStatusRepository;
import ru.luttsev.deals.service.DealStatusService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DealStatusServiceImpl implements DealStatusService {

    private final DealStatusRepository dealStatusRepository;

    @Override
    public List<DealStatus> getAll() {
        return dealStatusRepository.findAll();
    }

    @Override
    public DealStatus getById(String id) {
        return dealStatusRepository.findById(id).orElseThrow(
                () -> new DealStatusNotFoundException(id)
        );
    }

    @Override
    public DealStatus save(DealStatus entity) {
        return dealStatusRepository.save(entity);
    }

    @Override
    public void deleteById(String id) {
        dealStatusRepository.deleteById(id);
    }

}
