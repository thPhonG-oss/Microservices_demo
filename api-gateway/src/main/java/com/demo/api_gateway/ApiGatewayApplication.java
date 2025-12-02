package com.demo.api_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
		System.out.println("╔════════════════════════════════════════════════════════╗");
        System.out.println("║   API Gateway Started!                                 ║");
        System.out.println("║   Gateway URL: http://localhost:8081                   ║");
        System.out.println("║   Product API: http://localhost:8081/api/products      ║");
        System.out.println("║   Order API: http://localhost:8081/api/orders          ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");
	}

}
