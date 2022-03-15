package com.company.service;

import com.company.dto.OrderDto;
import com.company.model.Order;
import org.springframework.lang.NonNull;

public interface OrderService {

    Order create(@NonNull OrderDto orderDto);
}
