package com.gucardev.rabbitmqexample;

public class Constants {
    public static final String EXCHANGE = "rabbit_exchange";
    public static final String INTERNAL_QUEUE_START_PROCESS = "rabbit_queue";
    public static final String EXTERNAL_QUEUE_PROCESSOR_REQUEST = "rabbit_queue2";
    public static final String INTERNAL_QUEUE_PROCESSOR_RESPONSE = "rabbit_queue3";
    public static final String INTERNAL_QUEUE_START_PROCESS_ROUTING_KEY = "rabbit_routingKey";
    public static final String EXTERNAL_QUEUE_PROCESSOR_REQUEST_ROUTING_KEY = "rabbit_routingKey2";
    public static final String INTERNAL_QUEUE_PROCESSOR_RESPONSE_ROUTING_KEY = "rabbit_routingKey3";
}