package com.workflow.controller;

import com.workflow.dto.LoginRequest;
import com.workflow.dto.RegisterRequest;
import com.workflow.model.User;
import com.workflow.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Authentication endpoints")
@CrossOrigin(origins = {"http://localhost:5001", "http://localhost:5000", "http://localhost:3000"}, 
             allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, 
                                             RequestMethod.DELETE, RequestMethod.OPTIONS})
public class AuthController {

    @Autowired
    private AuthService authService;
    
    // Add OPTIONS method support for preflight requests
    @RequestMapping(method = RequestMethod.OPTIONS)
    public ResponseEntity<?> options() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    @Operation(summary = "Register new user")
    public ResponseEntity<Map<String, Object>> register(@RequestBody RegisterRequest request) {
        User user = authService.register(request.getUsername(), request.getEmail(), request.getPassword());
        String token = authService.generateToken(user);
        
        return ResponseEntity.ok(Map.of(
            "user", user,
            "token", token,
            "message", "User registered successfully"
        ));
    }

    @PostMapping("/login")
    @Operation(summary = "Login user")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest request) {
        User user = authService.authenticate(request.getUsername(), request.getPassword());
        String token = authService.generateToken(user);
        
        return ResponseEntity.ok(Map.of(
            "user", user,
            "token", token,
            "message", "Login successful"
        ));
    }

    // DTOs for auth requests
    public static class LoginRequest {
        private String username;
        private String password;
        
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    public static class RegisterRequest {
        private String username;
        private String email;
        private String password;
        
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
}
