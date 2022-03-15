package com.company.service;

import com.company.model.Customer;
import com.company.model.Warehouse;
import org.springframework.lang.NonNull;

public interface DistanceService {
    void asyncSetupDistance(Customer customer);

    Long findDistance(@NonNull Customer customer, @NonNull Warehouse warehouse);
}
