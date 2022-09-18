package com.kingston.msc.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Sandaka Wijesinghe.
 * Date: 8/11/22
 */
@Configuration
public class RabbitMQConfig {

    @Value("${learngenix.rabbitmq.cp.registration.queue}")
    String cpRegistrationQueue;

    @Value("${learngenix.rabbitmq.stu.registration.queue}")
    String stuRegistrationQueue;

    @Value("${learngenix.rabbitmq.exchange}")
    String exchange;

    @Value("${learngenix.rabbitmq.cp.registration.routingkey}")
    private String cpRoutingKey;

    @Value("${learngenix.rabbitmq.stu.registration.routingkey}")
    private String stuRoutingKey;

    @Bean
    Queue courseProviderQueue() {
        return new Queue(cpRegistrationQueue, true);
    }

    @Bean
    Queue studentQueue() {
        return new Queue(stuRegistrationQueue, true);
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    Binding courseProviderBinding(Queue courseProviderQueue, DirectExchange exchange) {
        return BindingBuilder.bind(courseProviderQueue).to(exchange).with(cpRoutingKey);
    }

    @Bean
    Binding studentBinding(Queue studentQueue, DirectExchange exchange) {
        return BindingBuilder.bind(studentQueue).to(exchange).with(stuRoutingKey);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }


    @Bean
    public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
