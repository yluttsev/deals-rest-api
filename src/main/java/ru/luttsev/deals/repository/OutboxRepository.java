package ru.luttsev.deals.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.luttsev.deals.model.MessageStatus;
import ru.luttsev.deals.model.entity.Outbox;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OutboxRepository extends JpaRepository<Outbox, UUID> {

    @Query("select o from Outbox o where o.contractorId = :contractorId")
    Optional<Outbox> findByContractorId(@Param("contractorId") String contractorId);

    @Query("select o from Outbox o where o.status = :status")
    List<Outbox> findByStatus(@Param("status") MessageStatus status);

}
