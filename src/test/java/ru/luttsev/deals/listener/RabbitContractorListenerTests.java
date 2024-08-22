package ru.luttsev.deals.listener;

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
import ru.luttsev.deals.TestRabbitListener;
import ru.luttsev.deals.model.payload.rabbitmq.ContractorRabbitMessage;
import ru.luttsev.deals.model.payload.rabbitmq.ContractorRabbitPayload;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Import({PostgresContainerConfig.class, TestRabbitListener.class})
@Testcontainers
class RabbitContractorListenerTests {

    public static String MESSAGE_TTL = "300000";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${rabbitmq.contractor-exchange}")
    private String contractorExchangeName;

    @Value("${rabbitmq.contractor-queue}")
    private String contractorQueueName;

    @Value("${rabbitmq.dead-queue}")
    private String deadQueueName;

    @Container
    static RabbitMQContainer rabbitMQContainer = new RabbitMQContainer(DockerImageName.parse("rabbitmq:4.0-rc-management-alpine"));

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry properties) {
        properties.add("spring.rabbitmq.host", rabbitMQContainer::getHost);
        properties.add("spring.rabbitmq.port", rabbitMQContainer::getAmqpPort);
        properties.add("spring.rabbitmq.username", rabbitMQContainer::getAdminUsername);
        properties.add("spring.rabbitmq.password", rabbitMQContainer::getAdminPassword);
        properties.add("rabbitmq.message-ttl", () -> MESSAGE_TTL);
    }

    @Test
    void testResendRabbitMessageInDeadQueueOnError() throws JsonProcessingException {
        ContractorRabbitMessage message = new ContractorRabbitMessage(
                ContractorRabbitPayload.builder()
                        .contractorId("Test contractor")
                        .name("Test name")
                        .inn("12345678")
                        .build(),
                LocalDateTime.now()
        );
        rabbitTemplate.convertAndSend(contractorExchangeName, contractorQueueName, objectMapper.writeValueAsString(message));

        await().atMost(10, TimeUnit.SECONDS).untilAsserted(() -> {
            assertEquals(1, rabbitAdmin.getQueueInfo(deadQueueName).getMessageCount());
            assertEquals(0, rabbitAdmin.getQueueInfo(contractorQueueName).getMessageCount());
        });
    }

}
