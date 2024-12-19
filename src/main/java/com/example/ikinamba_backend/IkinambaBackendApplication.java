package com.example.ikinamba_backend;

import com.example.ikinamba_backend.model.Role;
import com.example.ikinamba_backend.model.User;
import com.example.ikinamba_backend.repository.RoleRepository;
import com.example.ikinamba_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@SpringBootApplication
public class IkinambaBackendApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		// Set the server port to 8080
		System.setProperty("server.port", "8080");

		SpringApplication.run(IkinambaBackendApplication.class, args);
	}

	@Override
	public void run(String... args) {
		// Seed roles
		seedRoles();

		// Admin user seed
		seedAdminUser();
	}

	private void seedRoles() {
		String[] roles = {"admin", "client", "ikinamba"};

		for (String roleName : roles) {
			if (roleRepository.findByName(roleName) == null) {
				Role role = new Role(roleName);
				roleRepository.save(role);
				System.out.println("Seeded role: " + roleName);
			} else {
				System.out.println("Role already exists: " + roleName);
			}
		}
	}

	private void seedAdminUser() {
		String adminEmail = "admin@gmail.com";
		String adminPassword = "123";

		Optional<User> existingAdmin = userRepository.findByEmail(adminEmail);
		if (existingAdmin.isEmpty()) {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			User admin = new User();
			admin.setName("admin");
			admin.setEmail(adminEmail);
			admin.setPassword(passwordEncoder.encode(adminPassword));

			// Retrieve the admin role using Optional
			Optional<Role> adminRoleOpt = roleRepository.findByName("admin");
			if (adminRoleOpt.isPresent()) {
				admin.setRole(adminRoleOpt.get().getName());
				userRepository.save(admin);
				System.out.println("Admin user seeded successfully!");
			} else {
				System.out.println("Error: Admin role not found.");
				return;
			}
		} else {
			System.out.println("Admin user already exists!");
		}
	}
}