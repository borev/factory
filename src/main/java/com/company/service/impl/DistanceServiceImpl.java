package com.company.service.impl;

import com.company.dto.DistanceDto;
import com.company.error.NotFoundException;
import com.company.model.Customer;
import com.company.model.Distance;
import com.company.model.ProductItem;
import com.company.model.Warehouse;
import com.company.repository.DistanceRepository;
import com.company.repository.ProductItemRepository;
import com.company.service.DistanceService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static java.util.Collections.synchronizedList;
import static java.util.stream.Collectors.groupingBy;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "distance")
public class DistanceServiceImpl implements DistanceService {

    @Value("classpath:distance.json")
    private Resource resourceFile;
    private final ObjectMapper mapper;
    private final DistanceRepository distanceRepository;
    private final ProductItemRepository productItemRepository;
    private List<DistanceDto> data;

    @PostConstruct
    @SneakyThrows
    void onInit() {
        data = synchronizedList(mapper.readValue(
                resourceFile.getInputStream(),
                new TypeReference<List<DistanceDto>>() { }
        ));
    }

    @Async
    @Override
    public void asyncSetupDistance(Customer customer) {
        getAllWarehouses()
                .parallelStream()
                .forEach(warehouse -> calculateDistance(customer, warehouse));
    }

    @Override
    @Cacheable
    public Long findDistance(Customer customer, Warehouse warehouse) {
        try {
            return distanceRepository.findDistance(warehouse, customer);
        } catch (NotFoundException e) {
            //can be calculated directly
            return calculateDistance(customer, warehouse);
        }
    }

    private Long calculateDistance(Customer customer, Warehouse warehouse) {
        var miles = getProvidedDistance(warehouse, customer);
        var distance = Distance.builder()
                .miles(miles)
                .customer(customer)
                .warehouse(warehouse)
                .build();
        distanceRepository.save(distance);
        return miles;
    }

    private Set<Warehouse> getAllWarehouses() {
        return productItemRepository.findAll().stream()
                .collect(groupingBy(ProductItem::getWarehouse)).keySet();
    }

    private Long getProvidedDistance(Warehouse warehouse, Customer customer) {
        return data
                .stream()
                .filter(d ->
                        Objects.equals(d.getLocationA(), customer.getLocation()) && Objects.equals(d.getLocationB(), warehouse.getLocation()) ||
                                Objects.equals(d.getLocationA(), warehouse.getLocation()) && Objects.equals(d.getLocationB(), customer.getLocation())
                ).findFirst()
                .map(DistanceDto::getMiles)
                .orElseThrow(() -> new NotFoundException("Distance not found"));
    }

}
