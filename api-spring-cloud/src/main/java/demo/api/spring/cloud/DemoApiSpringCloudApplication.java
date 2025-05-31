package demo.api.spring.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class DemoApiSpringCloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApiSpringCloudApplication.class, args);
    }
}
