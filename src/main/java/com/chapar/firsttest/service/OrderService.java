package com.chapar.firsttest.service;

import com.chapar.firsttest.model.Order;

public interface OrderService {

    Order findCustomerOrderById(String customerId, Long orderId);

    Order addNewOrderForCustomer(String customerId, long productId, int count);

    long updateOrder(String customerId, long orderId, long productId, int count);

    long deleteOrder(String customerId, Long orderId);
}
