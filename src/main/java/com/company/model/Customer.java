package com.company.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class Customer implements Serializable {
    private Long id;
    private String login;
    private String location;
}
