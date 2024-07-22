package ru.luttsev.deals.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.luttsev.deals.model.entity.Outbox;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OutboxRepository extends JpaRepository<Outbox, UUID> {

    @Query("select o from Outbox o order by o.creationDate desc limit 1")
    Optional<Outbox> findLast();

}
