package com.example.ikinamba_backend.repository;

import com.example.ikinamba_backend.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Service, Long> {
    // Additional custom methods can be defined here if needed
}
