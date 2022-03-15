package com.company.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class Product implements Serializable {
    private Long id;
    private String name;
}
