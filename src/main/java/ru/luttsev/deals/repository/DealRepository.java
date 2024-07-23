package ru.luttsev.deals.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.luttsev.deals.model.entity.Deal;

import java.util.UUID;

@Repository
public interface DealRepository extends JpaRepository<Deal, UUID>, JpaSpecificationExecutor<Deal> {

    @Query("select count(d) from Deal d join d.contractors d_c join d.status d_s " +
            "where d_c.contractorId = :contractor_id and d_s.id = 'ACTIVE' and d.isActive = true")
    int numberOfActiveDealsByContractorId(@Param("contractor_id") String contractorId);

}
