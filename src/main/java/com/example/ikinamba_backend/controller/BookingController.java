package com.example.ikinamba_backend.controller;

import com.example.ikinamba_backend.model.Booking;
import com.example.ikinamba_backend.model.Service;
import com.example.ikinamba_backend.repository.BookingRepository;
import com.example.ikinamba_backend.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ServiceRepository serviceRepository; // Ensure this repository is injected

    // Endpoint to retrieve all bookings
    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    // Endpoint to create a new booking
    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody Booking booking) {
        if (booking.getService() == null || booking.getService().getId() == null) {
            return ResponseEntity.badRequest().body("Service ID is required");
        }

        Optional<Service> serviceOptional = serviceRepository.findById(booking.getService().getId());
        if (!serviceOptional.isPresent()) {
            return ResponseEntity.badRequest().body("Service not found with the provided ID");
        }

        // Set the service to the booking
        booking.setService(serviceOptional.get());
        booking.setDate(new Date()); // Optionally set the booking date

        Booking savedBooking = bookingRepository.save(booking);
        return ResponseEntity.ok(savedBooking);
    }
}
