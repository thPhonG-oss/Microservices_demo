package com.demo.product_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableDiscoveryClient
@EnableMongoRepositories
public class ProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
		System.out.println("╔════════════════════════════════════════════════════════╗");
        System.out.println("║         Product Service Started Successfully!          ║");
        System.out.println("║                                                        ║");
        System.out.println("║   Service URL: http://localhost:8082                   ║");
        System.out.println("║   API Base: http://localhost:8082/api/products         ║");
        System.out.println("║   Database: MongoDB (productdb)                        ║");
        System.out.println("║   Eureka: http://localhost:8761                        ║");
        System.out.println("║                                                        ║");
        System.out.println("║   Health Check: http://localhost:8082/actuator/health  ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");
	}

}
