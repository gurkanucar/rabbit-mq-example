package com.gucardev.rabbitmqexample;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class Listener {

  @RabbitListener(queues = Constants.QUEUE)
  public void consumeMessageFromQueue(MyData obj) {
    log.info("Message Received from queue: {} ", obj);
  }
}
