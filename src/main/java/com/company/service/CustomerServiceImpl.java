package com.company.service;

import com.company.dto.CustomerDto;
import com.company.model.Customer;
import com.company.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;
    private DistanceService distanceService;

    @Override
    public Customer create(CustomerDto dto) {
        var customer = Customer.builder()
                .location(dto.getLocation())
                .login(dto.getLogin())
                .build();

        var result= customerRepository.create(customer);
        distanceService.asyncSetupDistance(customer);
        return result;
    }

    @Override
    public Customer getById(Long id) {
        return customerRepository.getById(id);
    }

}
