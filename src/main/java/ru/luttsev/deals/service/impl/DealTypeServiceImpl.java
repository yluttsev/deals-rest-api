package ru.luttsev.deals.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.luttsev.deals.exception.DealTypeNotFoundException;
import ru.luttsev.deals.model.entity.DealType;
import ru.luttsev.deals.repository.DealTypeRepository;
import ru.luttsev.deals.service.CrudService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DealTypeServiceImpl implements CrudService<DealType, String> {

    private final DealTypeRepository dealTypeRepository;

    @Override
    public List<DealType> getAll() {
        return dealTypeRepository.findAll();
    }

    @Override
    public DealType getById(String id) {
        return dealTypeRepository.findById(id).orElseThrow(
                () -> new DealTypeNotFoundException(id)
        );
    }

    @Override
    public DealType save(DealType entity) {
        return dealTypeRepository.save(entity);
    }

    @Override
    public void deleteById(String id) {
        dealTypeRepository.deleteById(id);
    }

}