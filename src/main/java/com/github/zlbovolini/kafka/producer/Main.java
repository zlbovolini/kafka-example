package com.github.zlbovolini.kafka.producer;

import java.util.UUID;

public class Main {

    public static void main(String[] args) throws Exception {

        try (var dispatcher = new KafkaDispatcher()) {
            dispatcher.send("ECOMMERCE_NEW_ORDER", UUID.randomUUID().toString(), "Hello world");
            dispatcher.send("ECOMMERCE_SEND_EMAIL", UUID.randomUUID().toString(), "Hello, how are you?");
        }
    }


}
