package ru.luttsev.deals.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.luttsev.deals.exception.DealTypeNotFoundException;
import ru.luttsev.deals.model.entity.DealType;
import ru.luttsev.deals.repository.DealTypeRepository;
import ru.luttsev.deals.service.DealTypeService;

import java.util.List;

/**
 * Реализация сервиса для работы с типами сделки
 *
 * @author Yuri Luttsev
 */
@Service
@RequiredArgsConstructor
public class DealTypeServiceImpl implements DealTypeService {

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
