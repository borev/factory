package com.company.controller;

import com.company.dto.OrderDto;
import com.company.model.Order;
import com.company.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private OrderService orderService;

    @PostMapping
    public Order create(@RequestBody OrderDto orderDto) {
        return orderService.create(orderDto);
    }
}
