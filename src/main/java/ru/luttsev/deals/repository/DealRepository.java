package ru.luttsev.deals.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.luttsev.deals.model.entity.Deal;

import java.util.UUID;

@Repository
public interface DealRepository extends JpaRepository<Deal, UUID> {
}
