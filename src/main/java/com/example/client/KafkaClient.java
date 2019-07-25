package com.example.client;

import com.example.model.Product;
import com.example.service.ProductService;
import com.example.util.AsyncRunner;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class KafkaClient {
    private Consumer<Long, String> consumer;
    private ProductService productService;
    private AsyncRunner asyncRunner;

    @Autowired
    public KafkaClient(Consumer<Long, String> consumer, ProductService productService, AsyncRunner asyncRunner) {
        this.consumer = consumer;
        this.productService = productService;
        this.asyncRunner = asyncRunner;
    }

    @PostConstruct
    void start() {
        asyncRunner.run(this::getRecordsFromKafka);
    }

    private void getRecordsFromKafka() {
        ConsumerRecords<Long, String> consumerRecords = consumer.poll(1000);
        System.out.println("Got " + consumerRecords.count() + " product add request");
        for (ConsumerRecord<Long, String> record : consumerRecords) {
            productService.add(Product.get(record.value()));
        }
        consumer.commitSync();
    }
}
