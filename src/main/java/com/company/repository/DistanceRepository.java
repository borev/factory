package com.company.repository;

import com.company.model.Customer;
import com.company.model.Distance;
import com.company.model.Warehouse;

public interface DistanceRepository {
    Distance save(Distance order);
    Long findDistance(Warehouse warehouse, Customer customer);
}
