package com.company.service;

import com.company.dto.CustomerDto;
import com.company.model.Customer;
import org.springframework.lang.NonNull;

public interface CustomerService {

    Customer create(@NonNull CustomerDto customer);
    Customer getById(@NonNull Long id);
}
