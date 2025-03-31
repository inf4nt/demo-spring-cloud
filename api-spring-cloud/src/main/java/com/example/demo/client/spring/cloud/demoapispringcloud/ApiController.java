package com.example.demo.client.spring.cloud.demoapispringcloud;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping
public class ApiController {

    private final String instanceId = UUID.randomUUID().toString();

    @GetMapping
    public ApiResponse<String> getIndex() {
        return new ApiResponse<>(instanceId, "Hello World");
    }

    @GetMapping("/api")
    public ApiResponse<String> getApi() {
        return new ApiResponse<>(instanceId, UUID.randomUUID().toString());
    }

    public record ApiResponse<T>(String instanceId, T value) {
    }
}
