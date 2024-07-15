package ru.luttsev.deals.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.luttsev.deals.exception.ContractorRoleNotFoundException;
import ru.luttsev.deals.model.entity.ContractorRole;
import ru.luttsev.deals.repository.ContractorRoleRepository;
import ru.luttsev.deals.service.CrudService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContractorRoleServiceImpl implements CrudService<ContractorRole, String> {

    private final ContractorRoleRepository contractorRoleRepository;

    @Override
    public List<ContractorRole> getAll() {
        return contractorRoleRepository.findAll();
    }

    @Override
    public ContractorRole getById(String id) {
        return contractorRoleRepository.findById(id).orElseThrow(
                () -> new ContractorRoleNotFoundException(id)
        );
    }

    @Override
    public ContractorRole save(ContractorRole entity) {
        return contractorRoleRepository.save(entity);
    }

    @Override
    public void deleteById(String id) {
        contractorRoleRepository.deleteById(id);
    }

}
