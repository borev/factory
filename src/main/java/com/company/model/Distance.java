package com.company.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Distance {
    private Long id;
    private Customer customer;
    private Warehouse warehouse;
    private Long miles;
}
