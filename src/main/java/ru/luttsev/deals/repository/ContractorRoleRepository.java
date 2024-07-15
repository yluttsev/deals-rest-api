package ru.luttsev.deals.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.luttsev.deals.model.entity.ContractorRole;

@Repository
public interface ContractorRoleRepository extends JpaRepository<ContractorRole, String> {
}
