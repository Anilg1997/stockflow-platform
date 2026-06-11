package com.stockflow.execution;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.stockflow.execution", "com.stockflow.common"})
public class TradeExecutionApplication {
    public static void main(String[] args) {
        SpringApplication.run(TradeExecutionApplication.class, args);
    }
}
