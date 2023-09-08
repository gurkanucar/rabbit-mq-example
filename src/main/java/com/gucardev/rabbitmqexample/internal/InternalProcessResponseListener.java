package com.gucardev.rabbitmqexample.internal;

import com.gucardev.rabbitmqexample.Constants;
import com.gucardev.rabbitmqexample.MyData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class InternalProcessResponseListener {

  @RabbitListener(queues = Constants.INTERNAL_QUEUE_PROCESSOR_RESPONSE)
  public void consumeMessageFromQueue(MyData obj) {
    log.info("Message Received from internal process response queue: {} ", obj);
  }
}
