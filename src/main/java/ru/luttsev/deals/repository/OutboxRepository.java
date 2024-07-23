package ru.luttsev.deals.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.luttsev.deals.model.entity.Outbox;

import java.util.List;
import java.util.UUID;

@Repository
public interface OutboxRepository extends JpaRepository<Outbox, UUID> {

    @Query("select o from Outbox o where o.status = 'ERROR'")
    List<Outbox> findErrorMessages();

}
