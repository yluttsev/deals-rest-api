package ru.luttsev.deals.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.luttsev.deals.exception.DealContractorNotFoundException;
import ru.luttsev.deals.model.entity.DealContractor;
import ru.luttsev.deals.repository.DealContractorRepository;
import ru.luttsev.deals.service.DealContractorService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DealContractorServiceImpl implements DealContractorService {

    private final DealContractorRepository dealContractorRepository;

    @Override
    public List<DealContractor> getAll() {
        return dealContractorRepository.findAll();
    }

    @Override
    public DealContractor getById(UUID id) {
        return dealContractorRepository.findById(id).orElseThrow(
                () -> new DealContractorNotFoundException(id)
        );
    }

    @Override
    public DealContractor save(DealContractor entity) {
        return dealContractorRepository.save(entity);
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        DealContractor dealContractor = this.getById(id);
        dealContractor.setActive(false);
        this.save(dealContractor);
    }

}
