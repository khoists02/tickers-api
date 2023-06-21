package com.tickers.io.applicationapi.services;

import com.tickers.io.applicationapi.dto.StockDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSender {
    @Autowired
    private AmqpTemplate rabbitTemplate;
    @Autowired
    private Queue queue;
    private static Logger logger = LogManager.getLogger(RabbitMQSender.class.toString());
    public void send(StockDto stockDto) {
        rabbitTemplate.convertAndSend(queue.getName(), stockDto);
        logger.info("Sending Message to the Queue : " + stockDto.toString());
    }
}
