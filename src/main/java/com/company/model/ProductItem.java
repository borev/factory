package com.company.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class ProductItem implements Serializable {
    private Long id;
    private Long code;
    private Warehouse warehouse;
    private Product product;
}
