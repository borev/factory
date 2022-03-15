package com.company.service;

import com.company.model.Customer;
import com.company.model.ProductItem;
import com.company.repository.ProductItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;
import static java.util.Map.Entry.comparingByKey;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

@Service
@AllArgsConstructor
public class ProductItemServiceImpl implements ProductItemService {
    private ProductItemRepository productItemRepository;
    private DistanceService distanceService;

    @Override
    public List<ProductItem> findAll() {
        return productItemRepository.findAll();
    }

    public List<ProductItem> findInClosestWarehouse(Customer customer, String name) {
        var items = productItemRepository.findByProductName(name);
        return items.stream()
                .collect(groupingBy(ProductItem::getWarehouse))
                .entrySet()
                .stream()
                .collect(toMap(e -> distanceService.findDistance(customer, e.getKey()), Map.Entry::getValue))
                .entrySet()
                .stream()
                .min(comparingByKey())
                .map(Map.Entry::getValue)
                .orElse(emptyList());
    }

    @Override
    public boolean deleteAll(List<ProductItem> items) {
        return productItemRepository.deleteAll(items);
    }

}
