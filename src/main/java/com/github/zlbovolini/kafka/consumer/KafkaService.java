package com.github.zlbovolini.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.io.Closeable;
import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.function.Consumer;

public class KafkaService implements Closeable {

    private final String groupId;
    private final String topic;
    private final KafkaConsumer<String, String> consumer;
    private final Consumer<ConsumerRecord<String, String>> parse;

    KafkaService(String groupId, String topic, Consumer<ConsumerRecord<String, String>> parse) {
        this.groupId = groupId;
        this.topic = topic;
        this.consumer = new KafkaConsumer<>(properties(groupId));
        this.parse = parse;
    }

    void run() {
        consumer.subscribe(List.of(topic));

        while (true) {
            var records = consumer.poll(Duration.ofMillis(100));

            if (!records.isEmpty()) {
                System.out.println("Found " + records.count() + " records");

                for (var record : records) {
                    parse.accept(record);
                }
            }
        }
    }

    private static Properties properties(String groupId) {
        var properties = new Properties();

        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "1");

        return properties;
    }

    @Override
    public void close() {
        consumer.close();
    }
}
