package com.stockflow.holdings;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.stockflow.holdings", "com.stockflow.common"})
public class HoldingsServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(HoldingsServiceApplication.class, args);
    }
}
