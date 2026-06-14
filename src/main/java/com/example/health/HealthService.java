package com.example.health.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class HealthService {

    public Map<String, Object> getHealth() {
        log.info("Checking application health");
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("message", "Application is running");
        health.put("timestamp", System.currentTimeMillis());
        return health;
    }
}
