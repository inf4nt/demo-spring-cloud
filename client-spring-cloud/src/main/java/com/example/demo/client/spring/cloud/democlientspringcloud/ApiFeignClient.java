package com.example.demo.client.spring.cloud.democlientspringcloud;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
        name = "${api.service.name:api-spring-cloud}",
        path = "/api",
        dismiss404 = true
)
public interface ApiFeignClient {

    @GetMapping
    ApiResponse<?> getApi();

    record ApiResponse<T>(String instanceId, T value) {
    }
}
