package com.gucardev.rabbitmqexample;

import java.util.Random;
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

  private final Random random = new Random();

  @RabbitListener(queues = Constants.QUEUE)
  public void consumeMessageFromQueue(MyData obj) throws InterruptedException {

    log.info("Message Received from queue: {} ", obj);
    obj.setName(obj.getName() + "_UPDATED");

    int randomDelay = 3000 + random.nextInt(3000);
    Thread.sleep(randomDelay);

    rabbitTemplate.convertAndSend(Constants.EXCHANGE, Constants.ROUTING_KEY2, obj);
  }
}
