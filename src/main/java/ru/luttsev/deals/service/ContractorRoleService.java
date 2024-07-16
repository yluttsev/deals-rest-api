package ru.luttsev.deals.service;

import ru.luttsev.deals.model.entity.ContractorRole;
import ru.luttsev.deals.model.entity.DealContractor;

public interface ContractorRoleService extends CrudService<ContractorRole, String> {

    DealContractor addRole(String contractorId, String roleId);

    DealContractor deleteRole(String contractorId, String roleId);

}
