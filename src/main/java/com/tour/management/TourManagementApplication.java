package com.tour.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.tour.management.repository")
@EntityScan(basePackages = "com.tour.management.entity")
@EnableTransactionManagement
public class TourManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(TourManagementApplication.class, args);
    }
} 