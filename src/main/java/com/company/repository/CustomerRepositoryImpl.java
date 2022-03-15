package com.company.repository;

import com.company.error.NotFoundException;
import com.company.model.Customer;
import com.company.util.SequenceGenerator;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CustomerRepositoryImpl implements CustomerRepository {
    private final Map<Long, Customer> customers = new ConcurrentHashMap<>();
    private final SequenceGenerator gen = new SequenceGenerator();

    @Override
    public Customer create(Customer customer) {
            customer.setId(gen.getNext());
        customers.put(customer.getId(), customer);
        return customer;
    }

    @Override
    public Customer getById(Long id) {
        if (!customers.containsKey(id)) {
            throw new NotFoundException("customer not found");
        }
        return customers.get(id);
    }

}
