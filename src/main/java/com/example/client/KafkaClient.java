package com.example.client;

import com.example.model.Product;
import com.example.service.ProductService;
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

    @Autowired
    public KafkaClient(Consumer<Long, String> consumer, ProductService productService) {
        this.consumer = consumer;
        this.productService = productService;
    }

    @PostConstruct
    void start() {
        new Thread(this::getRecordsFromKafka).start();
    }

    private void getRecordsFromKafka() {
        while (true) {
            ConsumerRecords<Long, String> consumerRecords = consumer.poll(1000);
            System.out.println("Got " + consumerRecords.count() + " product add request");
            for (ConsumerRecord<Long, String> record : consumerRecords) {
                productService.add(Product.get(record.value()));
            }
            consumer.commitSync();
        }
    }
}
