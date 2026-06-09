package com.example.health.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.spark.sql.SparkSession;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class HealthService {

    private final SparkSession sparkSession;

    public HealthService(SparkSession sparkSession) {
        this.sparkSession = sparkSession;
    }

    public Map<String, Object> getHealth() {
        log.info("Checking application health");
        Map<String, Object> health = new HashMap<>();

        try {
            health.put("status", "UP");
            health.put("message", "Spark Delta Sharing App is running");
            health.put("spark_app_name", sparkSession.sparkContext().appName());
            health.put("spark_version", sparkSession.version());
            health.put("spark_status", "ACTIVE");
            health.put("timestamp", System.currentTimeMillis());
            return health;
        } catch (Exception e) {
            log.error("Error checking health", e);
            health.put("status", "DOWN");
            health.put("message", "Health check failed");
            health.put("error", e.getMessage());
            return health;
        }
    }
}
