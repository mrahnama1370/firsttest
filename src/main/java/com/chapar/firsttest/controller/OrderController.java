package com.chapar.firsttest.controller;

import com.chapar.firsttest.dto.OrderDto;
import com.chapar.firsttest.model.Order;
import com.chapar.firsttest.service.OrderServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {

    final OrderServiceImpl orderService;
    final ModelMapper modelMapper;

    @Autowired
    public OrderController(OrderServiceImpl orderService, ModelMapper modelMapper) {
        this.orderService = orderService;
        this.modelMapper = modelMapper;
    }


    /**
     *  This API is used to get an Order
     *
     * @param customerId id of the customer
     * @param orderId id of the order
     *
     * @return instance of the OrderDto
     */
    @GetMapping("/customers/{customerId}/orders/{orderId}")
    private ResponseEntity<OrderDto> readOrder(
            @PathVariable String customerId,
            @PathVariable Long orderId) {

        Order order = orderService.findCustomerOrderById(customerId, orderId);

        return new ResponseEntity<>(modelMapper.map(order, OrderDto.class), HttpStatus.OK);

    }

    /**
     * This API is used to add a new Order
     *
     * @param customerId  id of the customer
     * @param productId  id of the product
     * @param count count of product
     *
     * @return instance of the added OrderDto
     */
    @PostMapping("/customers/{customerId}/orders")
    private ResponseEntity<OrderDto> addNewOrder(
            @PathVariable String customerId,
            @RequestParam(required = true) long productId,
            @RequestParam(required = true) int count) {

        Order order = orderService.addNewOrderForCustomer(customerId, productId, count);

        return new ResponseEntity<>(modelMapper.map(order, OrderDto.class), HttpStatus.OK);

    }


    /**
     * This API is used to update an Order
     *
     * @param customerId id of the customer
     * @param orderId id of the order
     * @param productId  id of the product
     * @param count count of product
     *
     * @return id of the updated order
     */
    @PatchMapping("/customers/{customerId}/orders/{orderId}")
    private ResponseEntity<Long> updateAnOrder(
            @PathVariable String customerId,
            @PathVariable Long orderId,
            @RequestParam long productId,
            @RequestParam int count) {

        orderService.updateOrder(customerId, orderId, productId, count);

        return new ResponseEntity<>(orderId, HttpStatus.OK);

    }

    /**
     *  This API is used to delete an order
     *
     * @param customerId id of the customer
     * @param orderId id of the order
     *
     * @return id of the deleted order
     */
    @DeleteMapping("/customers/{customerId}/orders/{orderId}")
    private ResponseEntity<Long> deleteAnOrder(@PathVariable String customerId, @PathVariable Long orderId) {

        orderService.deleteOrder(customerId, orderId);

        return new ResponseEntity<>(orderId, HttpStatus.OK);

    }
}
