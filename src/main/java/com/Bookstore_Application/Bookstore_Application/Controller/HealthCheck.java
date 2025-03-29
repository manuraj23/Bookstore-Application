package com.Bookstore_Application.Bookstore_Application.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("healthCheck")
public class HealthCheck {
    @GetMapping("/healthCheck")
    public String healthCheck() {
        return "All Ok! All good!";
    }
}
