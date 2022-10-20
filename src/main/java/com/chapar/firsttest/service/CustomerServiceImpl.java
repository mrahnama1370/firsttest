package com.chapar.firsttest.service;

import com.chapar.firsttest.model.Customer;
import com.chapar.firsttest.model.QCustomer;
import com.chapar.firsttest.repository.CustomerRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    final CustomerRepository customerRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer findCustomerById(String customerId) {

        QCustomer customer = QCustomer.customer;

        JPAQueryFactory query = new JPAQueryFactory(entityManager);

        return Optional.ofNullable
                (query.selectFrom(customer).where(customer.id.eq(customerId)).fetchOne())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "customer " + customerId + " not found!"));

    }

    @Override
    public Customer addNewCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public long updateCustomer(String customerId, Customer customer) {

        QCustomer qCustomer = QCustomer.customer;

        JPAQueryFactory query = new JPAQueryFactory(entityManager);

        long resault = query.update(qCustomer)
                .set(qCustomer.description, customer.getDescription())
                .set(qCustomer.firstName, customer.getFirstName())
                .set(qCustomer.lastName, customer.getLastName())
                .set(qCustomer.email, customer.getEmail())
                .where(qCustomer.id.eq(customerId)).execute();

        if (resault == 0)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "customer " + customerId + " not found!");

        return resault;
    }

    @Override
    public void deleteCustomer(String customerId) {
        customerRepository.deleteById(customerId);
    }
}
