package com.company.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class Order implements Serializable {
    private Long id;
    private Customer customer;
    @EqualsAndHashCode.Exclude
    private List<ProductItem> items;
    private OrderStatus status;

}
