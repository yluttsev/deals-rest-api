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

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = PostgresContainerConfig.class)
@Transactional
class OutboxRepositoryTests {

    @Autowired
    private OutboxRepository outboxRepository;

    @Test
    @Sql("/db/outbox/insert_outbox_data.sql")
    @DisplayName("Получение сообщений со статусом ERROR")
    void testFindErrorMessages() {
        List<Outbox> errorMessages = outboxRepository.findErrorMessages();
        assertEquals(errorMessages.size(), 2);
    }

    @Test
    @Sql("/db/outbox/insert_outbox_data.sql")
    @DisplayName("Обновление статуса сообщения")
    void testUpdateMessageStatus() {
        List<Outbox> errorMessages = outboxRepository.findErrorMessages();
        errorMessages.forEach(message -> {
            outboxRepository.updateMessageStatus(message.getId(), MessageStatus.SUCCESS);
        });
        assertEquals(outboxRepository.findErrorMessages().size(), 0);
    }

}
