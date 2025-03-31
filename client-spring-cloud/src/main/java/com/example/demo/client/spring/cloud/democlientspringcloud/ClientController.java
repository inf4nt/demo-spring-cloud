package com.example.demo.client.spring.cloud.democlientspringcloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping
public class ClientController {

    private final String id = UUID.randomUUID().toString();

    @Autowired
    private ApiFeignClient apiFeignClient;

    @GetMapping
    public Object getClient() {
        ApiFeignClient.ApiResponse<?> api = apiFeignClient.getApi();
        return new ClientResponse<>(id, api);
    }

    public record ClientResponse<T>(String instanceId, T value) {}
}
