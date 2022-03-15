package com.company.service;

import com.company.dto.OrderDto;
import com.company.error.BusinessException;
import com.company.model.Order;
import com.company.repository.OrderRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.company.model.Status.RESERVED;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepositoryImpl orderRepository;
    private final ProductItemService productItemService;
    private final CustomerService customerService;

    @Override
    public Order create(OrderDto orderDto) {
        var customer = customerService.getById(orderDto.getCustomerId());
        var items = productItemService.findInClosestWarehouse(customer, orderDto.getProductName());
        var quantity = items.size();
        if (quantity < orderDto.getQuantity())
            throw new BusinessException(format("there are only %s product items", quantity));

        var orderItems = items.stream().limit(orderDto.getQuantity()).collect(toList());
        var deleted = productItemService.deleteAll(orderItems);
        if (!deleted) throw new BusinessException("product items already ordered");
        var order = Order.builder()
                .items(orderItems)
                .customer(customer)
                .status(RESERVED)
                .build();
        return orderRepository.save(order);
    }

}
