package com.company.repository;

import com.company.model.ProductItem;
import org.springframework.lang.NonNull;

import java.util.List;

public interface ProductItemRepository {
    List<ProductItem> findAll();
    List<ProductItem> findByProductName(@NonNull String name);
    ProductItem save(@NonNull ProductItem item);
    boolean deleteAll(@NonNull List<ProductItem> items);
}
