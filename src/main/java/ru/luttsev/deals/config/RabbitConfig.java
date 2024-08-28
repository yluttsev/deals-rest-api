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

    @Value("${rabbitmq.main-borrower-exchange}")
    private String mainBorrowerExchangeName;

    @Value("${rabbitmq.main-borrower-queue}")
    private String mainBorrowerQueueName;

    @Value("${rabbitmq.contractor-exchange}")
    private String contractorExchangeName;

    @Value("${rabbitmq.message-ttl}")
    private Integer messageTtl;

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
                .ttl(messageTtl)
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

    @Bean
    public DirectExchange contractorsContractorExchange() {
        return ExchangeBuilder
                .directExchange(contractorExchangeName)
                .durable(true)
                .build();
    }

    @Bean
    public Binding exchangeToQueueBinding() {
        return BindingBuilder
                .bind(dealsContractorQueue())
                .to(contractorsContractorExchange())
                .withQueueName();
    }

    @Bean
    public DirectExchange mainBorrowerExchange() {
        return ExchangeBuilder
                .directExchange(mainBorrowerExchangeName)
                .durable(true)
                .build();
    }

    @Bean
    public Queue mainBorrowerQueue() {
        return QueueBuilder
                .durable(mainBorrowerQueueName)
                .build();
    }

    @Bean
    public Binding mainBorrowerBinding() {
        return BindingBuilder
                .bind(mainBorrowerQueue())
                .to(mainBorrowerExchange())
                .withQueueName();
    }

}
