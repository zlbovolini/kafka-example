package com.github.zlbovolini.kafka.consumer;

import com.github.zlbovolini.kafka.producer.Order;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.Map;

public class FraudDetectorService {

    public static void main(String[] args) {
        try (var service = new KafkaService<>(FraudDetectorService.class.getSimpleName(),
                "ECOMMERCE_NEW_ORDER",
                FraudDetectorService::parse,
                Order.class,
                Map.of())) {
            service.run();
        }
    }

    private static void parse(ConsumerRecord<String, Order> record) {
        System.out.println("Key: " + record.key());
        System.out.println("Message: " + record.value());
        System.out.println("Partition: " + record.partition());
        System.out.println("Time: " + record.timestamp());
    }
}
