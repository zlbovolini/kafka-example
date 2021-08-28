package com.github.zlbovolini.kafka.producer;

import java.math.BigDecimal;

public class Order {

    private final String userId;
    private final String orderId;
    private final BigDecimal amount;

    Order(String userId, String orderId, BigDecimal amount) {
        this.userId = userId;
        this.orderId = orderId;
        this.amount = amount;
    }
}
