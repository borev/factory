package com.company.repository;

import com.company.model.ProductItem;
import com.company.util.SequenceGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.util.Collections.synchronizedList;
import static java.util.stream.Collectors.toList;

@Slf4j
@RequiredArgsConstructor
@Component
public class ProductItemRepositoryImpl implements ProductItemRepository {

    @Value("classpath:productItems.json")
    private Resource resourceFile;
    private final ObjectMapper mapper;
    private List<ProductItem> productItems;
    private SequenceGenerator gen;
    private Lock deleteLock;

    @PostConstruct
    @SneakyThrows
    void onInit() {
        deleteLock = new ReentrantLock();
        gen = new SequenceGenerator(10);
        var list = mapper.readValue(
                resourceFile.getInputStream(),
                new TypeReference<List<ProductItem>>() {  }
        );
        productItems = synchronizedList(list);
    }

    @Override
    public List<ProductItem> findAll() {
        return productItems;
    }

    @Override
    public List<ProductItem> findByProductName(String name) {
        return productItems.stream()
                .filter(item -> item.getProduct() != null && name.equalsIgnoreCase(item.getProduct().getName()))
                .collect(toList());
    }

    @Override
    public ProductItem save(ProductItem item) {
        item.setId(gen.getNext());
        productItems.add(item);
        return item;
    }

    @Override
    public boolean deleteAll(@NonNull List<ProductItem> items) {
        deleteLock.lock();
        try {
            return productItems.containsAll(items) && productItems.removeAll(items);
        } finally {
            deleteLock.unlock();
        }
    }

}
