package com.github.zlbovolini.ecommerce.neworder;

import com.github.zlbovolini.ecommerce.kafka.KafkaDispatcher;

import java.math.BigDecimal;
import java.util.UUID;

public class Main {

    public static void main(String[] args) throws Exception {

        try (var orderDispatcher = new KafkaDispatcher<Order>();
             var emailDispatcher = new KafkaDispatcher<Email>())
        {
            var userId = UUID.randomUUID().toString();
            var order = new Order(userId, UUID.randomUUID().toString(), BigDecimal.valueOf(10.99));
            var email = new Email("sub", "Hello, how are you?");

            orderDispatcher.send("ECOMMERCE_NEW_ORDER", userId, order);
            emailDispatcher.send("ECOMMERCE_SEND_EMAIL", userId, email);
        }
    }


}
