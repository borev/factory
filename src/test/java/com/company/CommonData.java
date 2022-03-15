package com.company;

import com.company.model.Customer;
import com.company.model.Product;
import com.company.model.ProductItem;
import com.company.model.Warehouse;


public class CommonData {

    public static ProductItem newProductItem(Long id, Long productId, Long warehouseId) {
        return ProductItem.builder()
                .id(id)
                .product(newProduct(productId))
                .warehouse(newWarehouse(warehouseId))
                .code(10000 + id)
                .build();
    }

    public static Customer newCustomer(Long id) {
        return Customer.builder()
                .id(id)
                .login("login" + id)
                .location("location" + id)
                .build();
    }

    public static Product newProduct(Long id) {
        return Product.builder().id(id).name("product" + id).build();
    }

    public static Warehouse newWarehouse(Long id) {
        return Warehouse.builder().id(id).name("warehouse" + id).location("location" + id).build();
    }
}
