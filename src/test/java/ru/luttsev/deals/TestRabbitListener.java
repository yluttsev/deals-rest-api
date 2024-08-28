package ru.luttsev.deals;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TestRabbitListener {

    @RabbitListener(queues = "deals_contractor_queue")
    public void listener() {
        throw new RuntimeException();
    }

}
