package com.example.demo.client.spring.cloud.demoeurekaspringcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class DemoEurekaSpringCloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoEurekaSpringCloudApplication.class, args);
    }
}
