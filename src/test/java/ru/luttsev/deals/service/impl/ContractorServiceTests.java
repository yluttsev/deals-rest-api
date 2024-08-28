package ru.luttsev.deals.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import ru.luttsev.deals.PostgresContainerConfig;
import ru.luttsev.deals.model.payload.rabbitmq.MainBorrowerRabbitMessage;

import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Testcontainers
@Import(PostgresContainerConfig.class)
class ContractorServiceTests {

    @Container
    static RabbitMQContainer rabbitMQContainer = new RabbitMQContainer(DockerImageName.parse("rabbitmq:4.0-rc-management-alpine"));

    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Value("${rabbitmq.main-borrower-exchange}")
    private String mainBorrowerExchangeName;

    @Value("${rabbitmq.main-borrower-queue}")
    private String mainBorrowerQueueName;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry properties) {
        properties.add("spring.rabbitmq.host", rabbitMQContainer::getHost);
        properties.add("spring.rabbitmq.port", rabbitMQContainer::getAmqpPort);
        properties.add("spring.rabbitmq.username", rabbitMQContainer::getAdminUsername);
        properties.add("spring.rabbitmq.password", rabbitMQContainer::getAdminPassword);
    }

    @Test
    void testSendMainBorrowerMessageInQueue() throws JsonProcessingException {
        MainBorrowerRabbitMessage message = MainBorrowerRabbitMessage.builder()
                .contractorId("test_contractor")
                .isMain(true)
                .build();
        rabbitTemplate.convertAndSend(mainBorrowerExchangeName, mainBorrowerQueueName, objectMapper.writeValueAsString(message));

        await().atMost(10, TimeUnit.SECONDS).untilAsserted(
                () -> assertEquals(1, rabbitAdmin.getQueueInfo(mainBorrowerQueueName).getMessageCount())
        );
    }

}
