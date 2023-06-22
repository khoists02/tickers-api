package com.tickers.io.applicationapi.services;

import com.tickers.io.applicationapi.dto.StockDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSender {
    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.routingkey}")
    private String routingkey;

    @Autowired
    private AmqpTemplate rabbitTemplate;


    private static Logger logger = LogManager.getLogger(RabbitMQSender.class.toString());
    public void send(StockDto stockDto) {
        rabbitTemplate.convertAndSend(exchange, routingkey , stockDto);
        logger.info("Sending Message to the Queue : " + stockDto.toString());
    }
}
