package ru.luttsev.deals.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.luttsev.deals.exception.DealContractorNotFoundException;
import ru.luttsev.deals.model.entity.DealContractor;
import ru.luttsev.deals.repository.DealContractorRepository;
import ru.luttsev.deals.service.CrudService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DealContractorServiceImpl implements CrudService<DealContractor, UUID> {

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
    public void deleteById(UUID id) {
        dealContractorRepository.deleteById(id);
    }

}
