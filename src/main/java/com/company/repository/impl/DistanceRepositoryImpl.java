package com.company.repository.impl;

import com.company.error.NotFoundException;
import com.company.model.Customer;
import com.company.model.Distance;
import com.company.model.Warehouse;
import com.company.repository.DistanceRepository;
import com.company.util.SequenceGenerator;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

import static java.util.Collections.synchronizedSet;

@Slf4j
@NoArgsConstructor
@Component
public class DistanceRepositoryImpl implements DistanceRepository {

    private final Set<Distance> distances = synchronizedSet(new HashSet<>());
    private final SequenceGenerator gen = new SequenceGenerator();

    @Override
    public Distance save(Distance distance) {
        distance.setId(gen.getNext());
        distances.add(distance);
        return distance;
    }

    @Override
    public Long findDistance(Warehouse warehouse, Customer customer) {
        return distances.stream()
                .filter(distance ->
                        distance.getCustomer().equals(customer) && distance.getWarehouse().equals(warehouse)
                ).findFirst()
                .map(Distance::getMiles)
                .orElseThrow(() -> new NotFoundException("Distance not found"));
    }
}
