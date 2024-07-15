package ru.luttsev.deals.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.luttsev.deals.model.entity.DealContractor;

import java.util.UUID;

@Repository
public interface DealContractorRepository extends JpaRepository<DealContractor, UUID> {
}
