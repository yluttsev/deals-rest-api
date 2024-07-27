package ru.luttsev.deals.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.luttsev.deals.model.entity.Deal;
import ru.luttsev.deals.model.entity.DealContractor;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DealRepository extends JpaRepository<Deal, UUID>, JpaSpecificationExecutor<Deal> {

    @Query("select count(d) from Deal d join d.contractors d_c join d.status d_s " +
            "where d_c.contractorId = :contractor_id and d_s.id = 'ACTIVE' and d.isActive = true")
    int numberOfActiveDealsByContractorId(@Param("contractor_id") String contractorId);

    @Query("select d_c from DealContractor d_c where d_c.deal.id = :dealId and d_c.main = true")
    Optional<DealContractor> findMainContractorByDealId(@Param("dealId") UUID dealId);

}
