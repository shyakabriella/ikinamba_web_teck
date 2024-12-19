package com.example.ikinamba_backend.controller;

import com.example.ikinamba_backend.model.User;
import com.example.ikinamba_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Changed from BCryptPasswordEncoder to PasswordEncoder

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> loginRequest) {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");

        Optional<User> userOptional = userRepository.findByEmail(email);

        Map<String, Object> response = new HashMap<>();

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Validate password
            if (passwordEncoder.matches(password, user.getPassword())) {
                // Successful login
                response.put("status", "success");
                response.put("message", "Login successful!");
                response.put("email", user.getEmail());
                response.put("role", user.getRole());

                // Response based on role
                switch (user.getRole()) {
                    case "admin":
                        response.put("dashboard", "/admin/dashboard");
                        break;
                    case "client":
                        response.put("dashboard", "/client/dashboard");
                        break;
                    case "ikinamba":
                        response.put("dashboard", "/ikinamba/dashboard");
                        break;
                    default:
                        response.put("dashboard", "/home");
                        break;
                }

                return response;
            } else {
                // Invalid password
                response.put("status", "error");
                response.put("message", "Invalid password!");
                return response;
            }
        } else {
            // User not found
            response.put("status", "error");
            response.put("message", "User not found!");
            return response;
        }
    }
}
