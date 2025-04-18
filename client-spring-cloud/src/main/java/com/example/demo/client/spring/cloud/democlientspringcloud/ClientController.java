package com.example.demo.client.spring.cloud.democlientspringcloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping
public class ClientController {

    private static final String instanceId = UUID.randomUUID().toString()
            .substring(0, 4);

    @Autowired
    private ApiFeignClient apiFeignClient;

    @GetMapping
    public Object getClient() {
        Object apiResponse = apiFeignClient.getApi();
        return Map.of(
                "instanceId", instanceId,
                "apiResponse", apiResponse
        );
    }
}
