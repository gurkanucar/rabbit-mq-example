package com.gucardev.rabbitmqexample.external;

import com.gucardev.rabbitmqexample.Constants;
import com.gucardev.rabbitmqexample.MyData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExternalProcessorListener {

  private final RabbitTemplate rabbitTemplate;
  // private final Random random = new Random();

  @RabbitListener(queues = Constants.EXTERNAL_QUEUE_PROCESSOR_REQUEST)
  public void consumeMessageFromQueue(MyData obj) {
    //  log.info("Message Received from external processor queue: {} ", obj);
    // obj.setName(obj.getName() + "_UPDATED");
    // rabbitTemplate.convertAndSend( Constants.EXCHANGE,
    // Constants.INTERNAL_QUEUE_PROCESSOR_RESPONSE_ROUTING_KEY, obj);
    //    obj.setName(obj.getName() + "_UPDATED");
    //
    //    int randomDelay = 3000 + random.nextInt(3000);
    //    Thread.sleep(randomDelay);
  }
}
