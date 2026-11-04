package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    
    @GetMapping("/")
    public String home() {
        return "Hello from CI/CD Pipeline Demo Application - Version 1.0!";
    }
    
    @GetMapping("/health")
    public String health() {
        return "Application is healthy!";
    }
}
