package ru.luttsev.deals.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.luttsev.deals.model.MessageStatus;
import ru.luttsev.deals.model.entity.Outbox;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OutboxRepositoryTests {

    @Autowired
    private OutboxRepository outboxRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    @DisplayName("Сохранение outbox сообщения в БД")
    void testSaveOutboxMessageById() {
        Outbox outboxMessage = Outbox.builder()
                .contractorId("test_id")
                .isMain(true)
                .creationDate(LocalDateTime.now())
                .status(MessageStatus.SUCCESS)
                .build();
        Outbox savedOutboxMessage = outboxRepository.save(outboxMessage);
        assertNotNull(testEntityManager.find(Outbox.class, savedOutboxMessage.getId()));
    }

    @Test
    @DisplayName("Получение последней записи в таблице Outbox")
    void testFindLastOutboxMessage() {
        Outbox outboxMessage = Outbox.builder()
                .contractorId("test_id")
                .isMain(true)
                .creationDate(LocalDateTime.now())
                .status(MessageStatus.SUCCESS)
                .build();
        Outbox persistOutbox = testEntityManager.persist(outboxMessage);
        assertAll(
                () -> assertTrue(outboxRepository.findLast().isPresent()),
                () -> assertEquals(outboxRepository.findLast().get().getId(), persistOutbox.getId())
        );
    }

}
