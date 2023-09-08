package com.gucardev.rabbitmqexample;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class Controller {
  private final RabbitTemplate rabbitTemplate;

  @GetMapping("/{name}")
  public String bookOrder(@PathVariable String name) {
    rabbitTemplate.convertAndSend(Constants.EXCHANGE, Constants.INTERNAL_QUEUE_START_PROCESS_ROUTING_KEY, new MyData(name));
    return "success!!";
  }
}
