package com.gucardev.rabbitmqexample;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class Listener {
  private final RabbitTemplate rabbitTemplate;

  @RabbitListener(queues = Constants.QUEUE)
  public void consumeMessageFromQueue(MyData obj) {
    log.info("Message Received from queue: {} ", obj);
    obj.setName(obj.getName() + "_UPDATED");
    rabbitTemplate.convertAndSend(Constants.EXCHANGE, Constants.ROUTING_KEY2, obj);
  }
}
