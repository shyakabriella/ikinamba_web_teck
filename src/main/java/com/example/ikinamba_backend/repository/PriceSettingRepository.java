package com.example.ikinamba_backend.repository;

import com.example.ikinamba_backend.model.PriceSetting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceSettingRepository extends JpaRepository<PriceSetting, Long> {
    // Additional queries can be defined here if necessary
}
