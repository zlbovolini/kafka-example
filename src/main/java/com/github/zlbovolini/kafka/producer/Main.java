package com.github.zlbovolini.kafka.producer;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.UUID;

public class Main {

    public static void main(String[] args) throws Exception {
        var producer = new KafkaProducer<String, String>(properties());

        var key = UUID.randomUUID().toString();
        var ecommerceNewOrder = new ProducerRecord<>("ECOMMERCE_NEW_ORDER", key, "Hello world");
        var ecommerceSendEmail = new ProducerRecord<>("ECOMMERCE_SEND_EMAIL", key, "Hello, how are you?");

        Callback callback = (metadata, exception) -> {
            if (exception != null) {
                exception.printStackTrace();
                return;
            }
            System.out.println("Partition: " + metadata.partition());
            System.out.println("Time: " + metadata.timestamp());
        };

        // blocking
        producer.send(ecommerceNewOrder, callback)
                .get();
        producer.send(ecommerceSendEmail, callback)
                .get();
    }

    private static Properties properties() {
        var properties = new Properties();

        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        return properties;
    }
}
