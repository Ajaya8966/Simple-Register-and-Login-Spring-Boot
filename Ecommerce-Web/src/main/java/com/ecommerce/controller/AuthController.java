package com.ecommerce.controller;

import com.ecommerce.model.User;
import com.ecommerce.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register"; // Thymeleaf template
    }

    @PostMapping("/register")
    public String registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Encrypt password
        user.setRole("ROLE_USER"); // Assign default role
        userRepository.save(user);
        return "redirect:/login?success"; // Redirect to login page after successful registration
    }
}
