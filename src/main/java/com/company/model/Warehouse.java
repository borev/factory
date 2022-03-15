package com.company.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class Warehouse implements Serializable {
    private Long id;
    private String name;
    private String location;
}
