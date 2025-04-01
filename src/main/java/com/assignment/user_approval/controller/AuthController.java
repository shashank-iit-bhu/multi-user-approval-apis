package com.assignment.user_approval.controller;

import com.assignment.user_approval.models.User;
import com.assignment.user_approval.service.UserService;
import com.assignment.user_approval.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final JwtUtils jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserService userService, JwtUtils jwtUtil, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");
        String email = request.get("email");
        User newUser = userService.registerUser(username, password, email);
        return ResponseEntity.ok(Map.of("message", "User registered successfully", "username", newUser.getName()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");

        User user = userService.authenticateUser(username, password);
        if (Objects.nonNull(user)) {
            String token = jwtUtil.generateToken(username);
            return ResponseEntity.ok(Map.of("token", token));
        }
        return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verifyToken(HttpServletRequest request) {

        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Invalid or missing token");
        }
        token = token.substring(7); // Remove "Bearer " prefix
        if (!jwtUtil.validateToken(token)) {
            return ResponseEntity.status(401).body("Token expired or invalid");
        }
        String username = jwtUtil.extractUsername(token);
        if (username == null) {
            return ResponseEntity.status(401).body("Invalid token");
        }
        return ResponseEntity.ok("User Verified: " + username);
    }
}

