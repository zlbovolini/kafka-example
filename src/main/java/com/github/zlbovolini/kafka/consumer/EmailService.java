package com.github.zlbovolini.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public class EmailService {

    public static void main(String[] args) {
        try (var service = new KafkaService(EmailService.class.getSimpleName(), "ECOMMERCE_SEND_EMAIL", EmailService::parse)) {
            service.run();
        }
    }

    private static void parse(ConsumerRecord<String, String> record) {
        System.out.println("Key: " + record.key());
        System.out.println("Message: " + record.value());
        System.out.println("Partition: " + record.partition());
        System.out.println("Time: " + record.timestamp());
    }
}
