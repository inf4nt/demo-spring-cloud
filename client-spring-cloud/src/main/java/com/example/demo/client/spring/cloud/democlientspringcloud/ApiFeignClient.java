package com.example.demo.client.spring.cloud.democlientspringcloud;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
        name = "${api.service.name}",
        path = "/api",
        dismiss404 = true
)
public interface ApiFeignClient {

    @GetMapping
    ResponseEntity<?> getApi();
}
