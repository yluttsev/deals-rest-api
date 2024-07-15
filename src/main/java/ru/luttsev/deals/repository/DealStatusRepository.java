package ru.luttsev.deals.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.luttsev.deals.model.entity.DealStatus;

@Repository
public interface DealStatusRepository extends JpaRepository<DealStatus, String> {
}
