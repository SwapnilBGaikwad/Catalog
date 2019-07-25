package com.example.client;

import com.example.service.ProductService;
import com.example.util.AsyncRunner;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.TopicPartition;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.*;

import static org.mockito.Mockito.*;

public class KafkaClientTest {
    private ArgumentCaptor<Runnable> captor = ArgumentCaptor.forClass(Runnable.class);
    private Consumer<Long, String> consumer;
    private AsyncRunner asyncRunner;
    private KafkaClient kafkaClient;

    @Before
    public void setUp() {
        consumer = mock(Consumer.class);
        ProductService productService = mock(ProductService.class);
        asyncRunner = mock(AsyncRunner.class);
        kafkaClient = new KafkaClient(consumer, productService, asyncRunner);
    }

    @Test
    public void shouldPollRecordsForKafkaAndAddToProductService() {
        ConsumerRecord<Long, String> record = getConsumerRecord();
        ConsumerRecords<Long, String> records = getConsumerRecords(record);
        when(consumer.poll(1000)).thenReturn(records);

        kafkaClient.start();
        verify(asyncRunner).run(captor.capture());
        Runnable runnable = captor.getValue();
        runnable.run();

        verify(consumer, times(1)).poll(1000);
        verify(consumer).commitSync();
    }

    private ConsumerRecords<Long,String> getConsumerRecords(ConsumerRecord<Long, String> record) {
        Map<TopicPartition, List<ConsumerRecord<Long,String>>> map = new HashMap<>();
        map.put(new TopicPartition("topic", 1), Collections.singletonList(record));
        return new ConsumerRecords<>(map);
    }

    private ConsumerRecord<Long, String> getConsumerRecord() {
        return new ConsumerRecord<>("test", 1, 1L, 1L, "{\"name\":\"P1\",\"price\":10.0}");
    }
}