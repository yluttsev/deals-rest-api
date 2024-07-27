package ru.luttsev.deals.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.luttsev.deals.PostgresContainerConfig;
import ru.luttsev.deals.model.MessageStatus;
import ru.luttsev.deals.model.entity.Outbox;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = PostgresContainerConfig.class)
@Transactional
class OutboxRepositoryTests {

    @Autowired
    private OutboxRepository outboxRepository;

    @Test
    @Sql("/db/outbox/insert_outbox_data.sql")
    @DisplayName("Получение статуса для contractorId")
    void testFindByContractorId() {
        Optional<Outbox> outbox = outboxRepository.findByContractorId("test_id");
        assertTrue(outbox.isPresent());
    }

    @Test
    @Sql("/db/outbox/insert_outbox_data.sql")
    @DisplayName("Получение всех сообщений с определенным статусом")
    void testFindByStatus() {
        List<Outbox> messages = outboxRepository.findByStatus(MessageStatus.CREATED);
        assertEquals(messages.size(), 2);
    }

}
