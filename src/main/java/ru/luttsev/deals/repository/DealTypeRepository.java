package ru.luttsev.deals.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.luttsev.deals.model.entity.DealType;

@Repository
public interface DealTypeRepository extends JpaRepository<DealType, String> {
}
