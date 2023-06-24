package com.tickers.io.applicationapi.api.rabbitmq;

import com.tickers.io.applicationapi.services.RabbitMQSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(name = "/rabbitmq")
public class RabbitMQController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RabbitMQSender sender;

    @PostMapping("/send")
    public void sendRabbitMq(@RequestBody String message) {
        sender.send(message);
        logger.info("Send Message Success {}", message);
    }

}
