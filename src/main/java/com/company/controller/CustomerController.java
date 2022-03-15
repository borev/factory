package com.company.controller;

import com.company.dto.CustomerDto;
import com.company.model.Customer;
import com.company.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private CustomerService customerService;

    @PostMapping
    public Customer create(@RequestBody CustomerDto customerDto) {
        return customerService.create(customerDto);
    }
}
