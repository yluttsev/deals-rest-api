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
import ru.luttsev.springbootstarterauditlib.LogLevel;
import ru.luttsev.springbootstarterauditlib.annotation.AuditLog;

/**
 * Контроллер для работы с ролями контрагентов
 *
 * @author Yuri Luttsev
 */
@RestController
@RequestMapping("/contractor-to-role")
@RequiredArgsConstructor
public class ContractorToRoleController {

    /**
     * {@link ContractorRoleService Сервис для работы с ролями контрагентов}
     */
    private final ContractorRoleService contractorRoleService;

    /**
     * {@link ModelMapper Маппер сущностей и DTO}
     */
    private final ModelMapper mapper;

    /**
     * Назначает роль контрагенту сделки
     *
     * @param payload {@link SetRoleToDealContractorPayload DTO установки роли}
     * @return {@link DealContractorPayload DTO контрагента сделки} с новой ролью
     */
    @AuditLog(logLevel = LogLevel.INFO)
    @PostMapping("/add")
    public DealContractorPayload addRoleToDealContractor(@RequestBody SetRoleToDealContractorPayload payload) {
        DealContractor dealContractor = contractorRoleService.addRole(payload.getContractorId(), payload.getRoleId());
        return mapper.map(dealContractor, DealContractorPayload.class);
    }

    /**
     * Удаляет роль контрагенту сделки
     *
     * @param payload {@link SetRoleToDealContractorPayload DTO установки роли}
     * @return {@link DealContractorPayload DTO контрагента сделки} с удаленной ролью
     */
    @AuditLog(logLevel = LogLevel.INFO)
    @DeleteMapping("/delete")
    public DealContractorPayload deleteRoleToDealContractor(@RequestBody SetRoleToDealContractorPayload payload) {
        DealContractor dealContractor = contractorRoleService.deleteRole(payload.getContractorId(), payload.getRoleId());
        return mapper.map(dealContractor, DealContractorPayload.class);
    }

}
