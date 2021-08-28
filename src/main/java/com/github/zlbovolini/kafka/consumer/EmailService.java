package com.github.zlbovolini.kafka.consumer;

import com.github.zlbovolini.kafka.producer.Email;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.Map;

public class EmailService {

    public static void main(String[] args) {
        try (var service = new KafkaService<>(EmailService.class.getSimpleName(),
                "ECOMMERCE_SEND_EMAIL",
                EmailService::parse,
                Email.class,
                Map.of())) {
            service.run();
        }
    }

    private static void parse(ConsumerRecord<String, Email> record) {
        System.out.println("Key: " + record.key());
        System.out.println("Message: " + record.value());
        System.out.println("Partition: " + record.partition());
        System.out.println("Time: " + record.timestamp());
    }
}
