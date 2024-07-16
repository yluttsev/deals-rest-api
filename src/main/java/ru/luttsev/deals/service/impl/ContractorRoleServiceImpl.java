package ru.luttsev.deals.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.luttsev.deals.exception.ContractorRoleNotFoundException;
import ru.luttsev.deals.model.entity.ContractorRole;
import ru.luttsev.deals.model.entity.DealContractor;
import ru.luttsev.deals.repository.ContractorRoleRepository;
import ru.luttsev.deals.service.ContractorRoleService;
import ru.luttsev.deals.service.DealContractorService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContractorRoleServiceImpl implements ContractorRoleService {

    private final ContractorRoleRepository contractorRoleRepository;

    private final DealContractorService dealContractorService;

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

    @Override
    @Transactional
    public DealContractor addRole(String contractorId, String roleId) {
        DealContractor dealContractor = dealContractorService.getById(UUID.fromString(contractorId));
        ContractorRole contractorRole = this.getById(roleId);
        dealContractor.getRoles().add(contractorRole);
        return dealContractorService.save(dealContractor);
    }

    @Override
    @Transactional
    public DealContractor deleteRole(String contractorId, String roleId) {
        DealContractor dealContractor = dealContractorService.getById(UUID.fromString(contractorId));
        ContractorRole contractorRole = this.getById(roleId);
        dealContractor.getRoles().remove(contractorRole);
        return dealContractorService.save(dealContractor);
    }
}
