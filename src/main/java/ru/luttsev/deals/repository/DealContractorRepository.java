package ru.luttsev.deals.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.luttsev.deals.model.entity.DealContractor;

import java.util.List;
import java.util.UUID;

@Repository
public interface DealContractorRepository extends JpaRepository<DealContractor, UUID> {

    @Query("select dc from DealContractor dc where dc.contractorId = ?1")
    List<DealContractor> findByContractorId(String contractorId);

}
