package com.chapar.firsttest.dto;

import com.chapar.firsttest.model.Customer;
import com.chapar.firsttest.model.Product;
import com.querydsl.core.annotations.Config;

import javax.persistence.*;

public class OrderDto {

    private Long id;
    private Integer count;
    private Customer customer;
    private Product product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
