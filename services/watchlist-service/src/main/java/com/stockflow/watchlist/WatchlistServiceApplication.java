package com.stockflow.watchlist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.stockflow.watchlist", "com.stockflow.common"})
public class WatchlistServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(WatchlistServiceApplication.class, args);
    }
}
