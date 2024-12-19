package com.example.ikinamba_backend.controller;

import com.example.ikinamba_backend.model.Service;
import com.example.ikinamba_backend.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/carwash")
public class CarWashController {

    @Autowired
    private ServiceRepository serviceRepository;

    // Endpoint to get all services
    @GetMapping("/services")
    public List<Service> getAllServices() {
        return serviceRepository.findAll();
    }

    // Endpoint to create a new service
    @PostMapping("/services")
    public ResponseEntity<Map<String, Object>> createService(@RequestBody Service service) {
        if (service.getName() == null || service.getPrice() == null) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "Service name and price are required");
            return ResponseEntity.badRequest().body(errorResponse);
        }

        Map<String, Object> response = new HashMap<>();
        Service savedService = serviceRepository.save(service);
        response.put("message", "Service created successfully!");
        response.put("service", savedService);

        return ResponseEntity.ok(response);
    }
}
