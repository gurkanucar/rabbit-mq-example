package com.gucardev.rabbitmqexample;

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
  @Bean(name = Constants.QUEUE)
  public Queue queue() {
    return new Queue(Constants.QUEUE);
  }

  @Bean(name = Constants.QUEUE2)
  public Queue queue2() {
    return new Queue(Constants.QUEUE2);
  }

  @Bean
  public TopicExchange exchange() {
    return new TopicExchange(Constants.EXCHANGE);
  }

  @Bean
  public Binding binding(@Qualifier(Constants.QUEUE) Queue queue, TopicExchange exchange) {
    return BindingBuilder.bind(queue).to(exchange).with(Constants.ROUTING_KEY);
  }

  @Bean
  public Binding binding2(@Qualifier(Constants.QUEUE2) Queue queue2, TopicExchange exchange) {
    return BindingBuilder.bind(queue2).to(exchange).with(Constants.ROUTING_KEY2);
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
}
