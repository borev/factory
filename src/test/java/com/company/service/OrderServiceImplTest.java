package com.company.service;

import com.company.dto.OrderDto;
import com.company.model.Order;
import com.company.model.Status;
import com.company.repository.OrderRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;

import static com.company.CommonData.newCustomer;
import static com.company.CommonData.newProductItem;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class OrderServiceImplTest {
    @Mock
    private OrderRepositoryImpl orderRepository;
    @Mock
    private ProductItemService productItemService;
    @Mock
    private CustomerService customerService;
    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    public void createOrder() {
        var items = new ArrayList<>(asList(
                newProductItem(1L, 1L, 1L),
                newProductItem(2L, 1L, 1L),
                newProductItem(3L, 1L, 1L)
        ));
        var customer = newCustomer(1L);
        var orderBuilder = Order.builder().customer(customer).items(items).status(Status.RESERVED);
        when(productItemService.findInClosestWarehouse(any(), any())).thenReturn(items);
        when(customerService.getById(anyLong())).thenReturn(customer);
        when(productItemService.deleteAll(anyList())).thenReturn(true);
        when(orderRepository.save(any())).thenReturn(orderBuilder.id(1L).build());
        var result = orderService.create(new OrderDto(1L,"product1", 3L));

        assertEquals(result.getId(), 1L);
        assertIterableEquals(result.getItems(), items);
        assertEquals(result.getCustomer(), customer);
        assertEquals(result.getStatus(), Status.RESERVED);

        verify(productItemService).findInClosestWarehouse(customer, "product1");
        verify(customerService).getById(1L);
        verify(productItemService).deleteAll(items);
        ArgumentCaptor<Order> captor = ArgumentCaptor.forClass(Order.class);

        verify(orderRepository).save(captor.capture());
        var actual = captor.getValue();
        assertEquals(actual.getCustomer(), customer);
        assertEquals(actual.getItems(), items);
        assertEquals(actual.getStatus(), Status.RESERVED);
    }

}
