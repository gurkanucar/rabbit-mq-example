package com.gucardev.rabbitmqexample.config;

import com.gucardev.rabbitmqexample.Constants;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MessagingConfig {

  @Bean
  public ConnectionFactory connectionFactory() {
    CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
    connectionFactory.setUsername("guest");
    connectionFactory.setPassword("guest");
    return connectionFactory;
  }

  @Bean
  public TopicExchange exchange() {
    return new TopicExchange(Constants.EXCHANGE);
  }

  @Bean
  public MessageConverter converter() {
    return new Jackson2JsonMessageConverter();
  }

  @Bean
  public AmqpTemplate template(ConnectionFactory connectionFactory) {
    final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(converter());
    return rabbitTemplate;
  }

  @Bean
  public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
    return new RabbitAdmin(connectionFactory);
  }

  @Bean
  public List<Queue> dynamicQueues(AmqpAdmin amqpAdmin) {
    List<Queue> queues = List.of(
            new Queue(Constants.INTERNAL_QUEUE_START_PROCESS),
            new Queue(Constants.EXTERNAL_QUEUE_PROCESSOR_REQUEST),
            new Queue(Constants.INTERNAL_QUEUE_PROCESSOR_RESPONSE));

    for (Queue queue : queues) {
      amqpAdmin.declareQueue(queue);
    }

    return queues;
  }

  @Bean
  public List<Binding> dynamicBindings(List<Queue> queues, TopicExchange exchange, AmqpAdmin amqpAdmin) {
    List<Binding> bindings = new ArrayList<>();

    for (int i = 0; i < queues.size(); i++) {
      Binding binding = BindingBuilder.bind(queues.get(i))
              .to(exchange)
              .with(getRoutingKeyForQueue(i));
      amqpAdmin.declareBinding(binding);
      bindings.add(binding);
    }

    return bindings;
  }

  private String getRoutingKeyForQueue(int index) {
    switch (index) {
      case 0:
        return Constants.INTERNAL_QUEUE_START_PROCESS_ROUTING_KEY;
      case 1:
        return Constants.EXTERNAL_QUEUE_PROCESSOR_REQUEST_ROUTING_KEY;
      case 2:
        return Constants.INTERNAL_QUEUE_PROCESSOR_RESPONSE_ROUTING_KEY;
      default:
        throw new IllegalArgumentException("Invalid queue index");
    }
  }
}
