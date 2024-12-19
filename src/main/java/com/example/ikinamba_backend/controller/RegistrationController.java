package com.example.ikinamba_backend.controller;

import com.example.ikinamba_backend.model.Role;
import com.example.ikinamba_backend.model.User;
import com.example.ikinamba_backend.repository.RoleRepository;
import com.example.ikinamba_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody Map<String, String> registrationData) {
        String email = registrationData.get("email");
        String password = registrationData.get("password");
        String name = registrationData.get("name");
        String roleName = registrationData.get("role");

        Map<String, Object> response = new HashMap<>();

        // Check if user already exists
        if (userRepository.findByEmail(email).isPresent()) {
            response.put("message", "Email already in use");
            return ResponseEntity.badRequest().body(response);
        }

        // Create and save the new user
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setName(name);

        // Assign role
        roleRepository.findByName(roleName).ifPresentOrElse(
                role -> newUser.setRole(role.getName()), // If role is found, set it
                () -> newUser.setRole("client") // If role is not found, default to "client"
        );

        userRepository.save(newUser);

        response.put("message", "User registered successfully");
        return ResponseEntity.ok(response);
    }
}
