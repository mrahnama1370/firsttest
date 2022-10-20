package com.chapar.firsttest.service;

import com.chapar.firsttest.model.Customer;

public interface CustomerService {

    Customer findCustomerById(String customerId);

    Customer addNewCustomer(Customer customer);

    long updateCustomer(String customerId, Customer customer);

    void deleteCustomer(String customerId);

}
