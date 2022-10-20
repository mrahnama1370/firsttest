package com.chapar.firsttest.service;

import com.chapar.firsttest.model.Order;
import com.chapar.firsttest.model.QOrder;
import com.chapar.firsttest.repository.OrderRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    final OrderRepository orderRepository;
    final CustomerServiceImpl customerService;
    final ProductService productService;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, CustomerServiceImpl customerService, ProductService productService) {
        this.orderRepository = orderRepository;
        this.customerService = customerService;
        this.productService = productService;
    }

    @Override
    public Order findCustomerOrderById(String customerId, Long orderId) {

        QOrder order = QOrder.order;

        JPAQueryFactory query = new JPAQueryFactory(entityManager);

        return Optional.ofNullable(
                query.selectFrom(order).where(order.customer().id.eq(customerId).and(order.id.eq(orderId))).fetchOne())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "order " + orderId + " not found!"));

    }

    @Override
    public Order addNewOrderForCustomer(String customerId, long productId, int count) {

        Order order = new Order();
        order.setCustomer(customerService.findCustomerById(customerId));
        order.setProduct(productService.findProductById(productId));
        order.setCount(count);

        return orderRepository.save(order);
    }

    @Override
    public long updateOrder(String customerId, long orderId, long productId, int count) {

        QOrder qOrder = QOrder.order;

        JPAQueryFactory query = new JPAQueryFactory(entityManager);

        long resault = query.update(qOrder)
                .set(qOrder.product(), productService.findProductById(productId))
                .set(qOrder.count, count)
                .where(qOrder.customer().id.eq(customerId).and(qOrder.id.eq(orderId))).execute();

        if (resault == 0)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "customer " + customerId + " not found!");

        return resault;
    }

    @Override
    public long deleteOrder(String customerId, Long orderId) {

        QOrder order = QOrder.order;

        JPAQueryFactory query = new JPAQueryFactory(entityManager);

        return query.delete(order).where(order.customer().id.eq(customerId).and(order.id.eq(orderId))).execute();
    }
}
