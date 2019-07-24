package com.example.model;

public interface IKafkaConstants {
    String KAFKA_BROKERS = "localhost:9092";
    String TOPIC_NAME = "product";
    String GROUP_ID_CONFIG = "consumerGroup1";
    String OFFSET_RESET_EARLIER = "earliest";
    Integer MAX_POLL_RECORDS = 1;

}
