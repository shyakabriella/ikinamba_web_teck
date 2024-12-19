package com.example.ikinamba_backend.repository;

import com.example.ikinamba_backend.model.CustomerRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRequestRepository extends JpaRepository<CustomerRequest, Long> {
}
