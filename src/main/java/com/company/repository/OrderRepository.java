package com.company.repository;

import com.company.model.Order;

public interface OrderRepository {
    Order save(Order order);
}
