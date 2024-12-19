package com.example.ikinamba_backend.repository;

import com.example.ikinamba_backend.model.CarWashStation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarWashStationRepository extends JpaRepository<CarWashStation, Long> {
    // Custom database queries can be defined here if needed
}
