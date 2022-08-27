package com.kingston.msc.rabbitmq;

import com.kingston.msc.entity.CPTransactionTracker;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by Sandaka Wijesinghe.
 * Date: 8/11/22
 */
@Service
public class RabbitMQSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Value("${learngenix.rabbitmq.exchange}")
    private String exchange;

    @Value("${learngenix.rabbitmq.routingkey}")
    private String routingkey;

    public void sendToCourseProviderQueue(CPTransactionTracker transactionTracker) {
        rabbitTemplate.convertAndSend(exchange, routingkey, transactionTracker);
        System.out.println("Send msg = " + transactionTracker);

    }

    public void sendToStudentQueue(CPTransactionTracker transactionTracker) {
        rabbitTemplate.convertAndSend(exchange, routingkey, transactionTracker);
        System.out.println("Send msg = " + transactionTracker);

    }
}
