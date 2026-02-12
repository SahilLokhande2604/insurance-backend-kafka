package com.spring.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.model.User;
import com.spring.repo.UserRepository;
import com.spring.util.JwtUtil;

import org.springframework.web.bind.annotation.*;

import io.jsonwebtoken.Claims;

@RestController
@RequestMapping("/api/users")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password, @RequestParam String role) {
//        Optional<User> userOpt = userRepository.findByUsername(username);
//        if (userOpt.isPresent() && userOpt.get().getPassword().equals(password) && userOpt.get().getRole().equals(role)) {
//            String token = JwtUtil.generateToken(username,role);
//            return ResponseEntity.ok(token);
//        }
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
//    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(
    		@RequestBody RegisterRequest request) {
    	 String username = request.getUsername();
         String password = request.getPassword();
        Optional<User> userOpt = userRepository.findByUsername(username);
     
        if (userOpt.isEmpty() || !userOpt.get().getPassword().equals(password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid credentials");
        }
     
        User user = userOpt.get();
        String token = JwtUtil.generateToken(user.getUsername(), user.getRole());
     
        return ResponseEntity.ok(token);
    }
     
  

    @GetMapping("/secure")
    public ResponseEntity<String> secure(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        if (JwtUtil.validateToken(token)) {
            String user = JwtUtil.getUsername(token);
            Claims claims = JwtUtil.getClaims(token);
            String role = claims.get("role", String.class);
            if(role.equalsIgnoreCase("ROLE_ADMIN"))
            {
            	return ResponseEntity.ok("Hello " + user + ", you accessed a secure ADMIN endpoint!");
            }
            else if(role.equalsIgnoreCase("ROLE_USER"))
            {
            	 return ResponseEntity.ok("Hello " + user + ", you accessed a secure USER endpoint!");
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
    }
    
    
    
//    @PostMapping("/register")
//    public ResponseEntity<String> register(@RequestBody String username, @RequestBody String password) {
//        if (userRepository.existsById(username)) {
//            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
//        }
//
//        User newUser = new User();
//        newUser.setUsername(username);
//        newUser.setPassword(password); // In production, hash this!
//        newUser.setRole("ROLE_USER");  // Default role
//
//        userRepository.save(newUser);
//        return ResponseEntity.ok("User registered successfully");
//    }
    
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();
        if (userRepository.existsById(username)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
        }
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password); // hash in production!
        newUser.setRole("ROLE_USER");
        userRepository.save(newUser);
        return ResponseEntity.ok("User registered successfully");
    }
    
    
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userRepository.findById(id.toString())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

}
