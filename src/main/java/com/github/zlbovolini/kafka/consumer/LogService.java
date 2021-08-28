package com.github.zlbovolini.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Map;
import java.util.regex.Pattern;

public class LogService {

    public static void main(String[] args) {
        try (var logService = new KafkaService<>(LogService.class.getSimpleName(),
                Pattern.compile("ECOMMERCE.*"),
                LogService::parse,
                String.class,
                Map.of(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName()))) {
            logService.run();
        }
    }

    private static void parse(ConsumerRecord<String, String> record) {
        System.out.println("Key: " + record.key());
        System.out.println("Message: " + record.value());
        System.out.println("Partition: " + record.partition());
        System.out.println("Time: " + record.timestamp());
    }
}
