package com.gucardev.rabbitmqexample;

import java.util.List;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingConfig {
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
  public List<Queue> dynamicQueues() {
    return List.of(new Queue(Constants.QUEUE), new Queue(Constants.QUEUE2));
  }

  @Bean
  public List<Binding> dynamicBindings(
      @Qualifier("dynamicQueues") List<Queue> queues, TopicExchange exchange) {
    return List.of(
        BindingBuilder.bind(queues.get(0)).to(exchange).with(Constants.ROUTING_KEY),
        BindingBuilder.bind(queues.get(1)).to(exchange).with(Constants.ROUTING_KEY2));
  }
}
