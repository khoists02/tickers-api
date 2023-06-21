package com.tickers.io.applicationapi.services;

import com.tickers.io.applicationapi.dto.StockDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "rabbitmq.queue", id = "listener")
public class RabbitMQReceiver {
    private static Logger logger = LogManager.getLogger(RabbitMQReceiver.class.toString());
    @RabbitHandler
    public void receiver(StockDto stockDto) {
        logger.info("MenuOrder listener invoked - Consuming Message with MenuOrder Identifier : " + stockDto.getClose());
    }
}
