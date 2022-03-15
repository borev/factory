package com.company.service;

import com.company.model.Customer;
import com.company.model.ProductItem;
import org.springframework.lang.NonNull;

import java.util.List;

public interface ProductItemService {

    List<ProductItem> findAll();
    List<ProductItem> findInClosestWarehouse(@NonNull Customer customer, String name);
    boolean deleteAll(List<ProductItem> items);
}
