package ru.luttsev.deals.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.luttsev.deals.model.entity.DealContractor;
import ru.luttsev.deals.model.payload.dealcontractor.DealContractorPayload;
import ru.luttsev.deals.model.payload.dealcontractor.SetRoleToDealContractorPayload;
import ru.luttsev.deals.service.ContractorRoleService;

@RestController
@RequestMapping("/contractor-to-role")
@RequiredArgsConstructor
public class ContractorToRoleController {

    private final ContractorRoleService contractorRoleService;

    private final ModelMapper mapper;

    @PostMapping("/add")
    public DealContractorPayload addRoleToDealContractor(@RequestBody SetRoleToDealContractorPayload payload) {
        DealContractor dealContractor = contractorRoleService.addRole(payload.getContractorId(), payload.getRoleId());
        return mapper.map(dealContractor, DealContractorPayload.class);
    }

    @DeleteMapping("/delete")
    public DealContractorPayload deleteRoleToDealContractor(@RequestBody SetRoleToDealContractorPayload payload) {
        DealContractor dealContractor = contractorRoleService.deleteRole(payload.getContractorId(), payload.getRoleId());
        return mapper.map(dealContractor, DealContractorPayload.class);
    }

}
