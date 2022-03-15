package com.company.repository;

import com.company.model.Customer;
import org.springframework.lang.NonNull;

public interface CustomerRepository {
    Customer create(@NonNull Customer customer);
    Customer getById(@NonNull Long id);
}
