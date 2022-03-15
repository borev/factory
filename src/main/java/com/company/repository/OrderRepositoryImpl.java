package com.company.repository;

import com.company.model.Order;
import com.company.util.SequenceGenerator;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@NoArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final Map<Long, Order> orders = new ConcurrentHashMap<>();
    private final SequenceGenerator gen = new SequenceGenerator();

    @Override
    public Order save(Order order) {
        order.setId(gen.getNext());
        orders.put(order.getId(), order);
        return order;
    }


}
