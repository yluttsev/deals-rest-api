package ru.luttsev.deals.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class RabbitConfig {

    @Value("${rabbitmq.contractor-queue}")
    private String queueName;

    @Value("${rabbitmq.dead-exchange}")
    private String deadExchangeName;

    @Value("${rabbitmq.dead-queue}")
    private String deadQueueName;

    @Value("${rabbitmq.dead-contractor-exchange}")
    private String deadContractorExchangeName;

    @Bean
    public Queue dealsContractorQueue() {
        return QueueBuilder
                .durable(queueName)
                .deadLetterExchange(deadExchangeName)
                .build();
    }

    @Bean
    public Queue dealContractorDeadQueue() {
        return QueueBuilder
                .durable(deadQueueName)
                .deadLetterExchange(deadContractorExchangeName)
                .ttl(300000)
                .build();
    }

    @Bean
    public FanoutExchange dealsDeadExchange() {
        return ExchangeBuilder
                .fanoutExchange(deadExchangeName)
                .durable(true)
                .build();
    }

    @Bean
    public DirectExchange dealContractorDeadExchange() {
        return ExchangeBuilder
                .directExchange(deadContractorExchangeName)
                .durable(true)
                .build();
    }

    @Bean
    public Binding deadQueueBinding() {
        return BindingBuilder
                .bind(dealContractorDeadQueue())
                .to(dealsDeadExchange());
    }

}
