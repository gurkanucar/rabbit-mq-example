package com.gucardev.rabbitmqexample.internal;

import com.gucardev.rabbitmqexample.Constants;
import com.gucardev.rabbitmqexample.MyData;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class StartProcessListener {
  private final RabbitTemplate rabbitTemplate;

  @RabbitListener(queues = Constants.INTERNAL_QUEUE_START_PROCESS)
  public void consumeMessageFromQueue(MyData obj) throws InterruptedException {
    int randomDelay = 3000 + new Random().nextInt(3000);
    Thread.sleep(randomDelay);
    log.info("Message Received from start process queue: {} ", obj);
    rabbitTemplate.convertAndSend(
        Constants.EXCHANGE, Constants.EXTERNAL_QUEUE_PROCESSOR_REQUEST_ROUTING_KEY, obj);
  }
}
