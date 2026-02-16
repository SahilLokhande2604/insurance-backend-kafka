package com.spring.controller;

import java.util.List;
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
//import user_service.kafka.UserEventProducer;

import io.jsonwebtoken.Claims;

@RestController
@RequestMapping("/api/users")
public class AuthController {

//    @Autowired
//    private UserEventProducer userEventProducer;

    @Autowired
    private UserRepository userRepository;

    
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

    
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();
        String name = request.getName();
        String phone = request.getPhone();
        if (userRepository.existsById(username)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
        }
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password); // hash in production!
        newUser.setRole("ROLE_USER");
        newUser.setName(name);
        newUser.setPhone(phone);
        userRepository.save(newUser);
//        userEventProducer.sendUserEvent("New user registered: " + username);
        return ResponseEntity.ok("User registered successfully");
    }
    
    
    @GetMapping("/{id}")
    public User getUserById(@PathVariable String id) {
        return userRepository.findById(id.toString())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }
    
    
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization")String authHeader){
    	if(authHeader == null || !authHeader.startsWith("Bearer")) {
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No token provided");
    	}
    	return ResponseEntity.ok("Logged out successfully");   	
    }
    
    @GetMapping("/by-username/{username}")
    public Optional<User> getUserByUsername(@PathVariable String username) {
        return userRepository.findByUsername(username);
    }
    
    @GetMapping()
    public List<User> getAllUser() {
        return userRepository.findAll();
    }
    
    

    
    

    
    
}
