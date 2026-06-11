package com.stockflow.brokerage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.stockflow.brokerage", "com.stockflow.common"})
public class BrokerageServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BrokerageServiceApplication.class, args);
    }
}
