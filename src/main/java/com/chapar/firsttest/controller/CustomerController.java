package com.chapar.firsttest.controller;

import com.chapar.firsttest.dto.CustomerDto;
import com.chapar.firsttest.model.Customer;
import com.chapar.firsttest.service.CustomerServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerController {

    final CustomerServiceImpl customerService;
    final ModelMapper modelMapper;

    @Autowired
    public CustomerController(CustomerServiceImpl customerService, ModelMapper modelMapper) {
        this.customerService = customerService;
        this.modelMapper = modelMapper;
    }

    /**
     * THis API is used to get a Customer
     *
     * @param customerId id of the customer
     *
     * @return instance of the CustomerDto
     */
    @GetMapping("/customers/{customerId}")
    public ResponseEntity<CustomerDto> readCustomer(
            @PathVariable String customerId) {

        Customer customer = customerService.findCustomerById(customerId);

        return new ResponseEntity<>(modelMapper.map(customer, CustomerDto.class), HttpStatus.OK);

    }

    /**
     *  This API is used to add a new Customer
     *
     * @param customerDto the customer that we want to add
     *
     * @return instance of the added CustomerDto
     */
    @PostMapping("/customers")
    public ResponseEntity<CustomerDto> addNewCustomer(
            @RequestBody CustomerDto customerDto) {

        Customer customer = customerService.addNewCustomer(modelMapper.map(customerDto, Customer.class));

        return new ResponseEntity<>(modelMapper.map(customer, CustomerDto.class), HttpStatus.OK);

    }

    /**
     * This API is used to update a Customer
     *
     * @param customerId id of the customer
     * @param customerDto updated customer
     *
     * @return id of the updated customer
     */
    @PatchMapping("/customers/{customerId}")
    public ResponseEntity<String> updateAnCustomer(
            @PathVariable String customerId,
            @RequestBody CustomerDto customerDto) {

        customerService.updateCustomer(customerId, modelMapper.map(customerDto, Customer.class));

        return new ResponseEntity<>(customerId, HttpStatus.OK);

    }

    /**
     * This API is used to delete a customer
     * @param customerId id of the customer
     *
     * @return id of the deleted customer
     */
    @DeleteMapping("/customers/{customerId}")
    public ResponseEntity<String> deleteAnCustomer(
            @PathVariable String customerId) {

        customerService.deleteCustomer(customerId);

        return new ResponseEntity<>(customerId, HttpStatus.OK);

    }
}
