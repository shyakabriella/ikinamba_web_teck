package com.example.ikinamba_backend.controller;

import com.example.ikinamba_backend.model.CustomerRequest;
import com.example.ikinamba_backend.repository.CustomerRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/requests")
public class RequestController {

    @Autowired
    private CustomerRequestRepository requestRepository;

    // Get all requests
    @GetMapping
    public List<CustomerRequest> getAllRequests() {
        return requestRepository.findAll();
    }

    // Create a new request
    @PostMapping
    public CustomerRequest createRequest(@RequestBody CustomerRequest request) {
        request.setStatus("pending");
        return requestRepository.save(request);
    }

    // Update request status (accept/reject)
    @PutMapping("/{id}")
    public ResponseEntity<CustomerRequest> updateRequestStatus(@PathVariable Long id, @RequestParam String status) {
        Optional<CustomerRequest> requestOptional = requestRepository.findById(id);
        if (!requestOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        CustomerRequest request = requestOptional.get();
        request.setStatus(status);
        requestRepository.save(request);

        return ResponseEntity.ok(request);
    }
}
