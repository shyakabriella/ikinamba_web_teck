package com.example.ikinamba_backend.repository;

import com.example.ikinamba_backend.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    // Additional custom methods can be defined here if needed
}
