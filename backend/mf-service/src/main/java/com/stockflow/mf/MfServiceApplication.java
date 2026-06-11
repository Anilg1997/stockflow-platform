package com.stockflow.mf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication @EnableDiscoveryClient
public class MfServiceApplication {
    public static void main(String[] args) { SpringApplication.run(MfServiceApplication.class, args); }
}
