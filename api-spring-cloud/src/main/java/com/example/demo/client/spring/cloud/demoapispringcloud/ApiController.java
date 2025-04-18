package com.example.demo.client.spring.cloud.demoapispringcloud;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping
public class ApiController {

    private static final String instanceId = UUID.randomUUID().toString()
            .substring(0, 4);

    @GetMapping
    public Object getIndex() {
        return Map.of(
                "instanceId", instanceId,
                "value", "Hello world"
        );
    }

    @GetMapping("/api")
    public Object getApi() {
        return Map.of(
                "instanceId", instanceId,
                "value", UUID.randomUUID().toString()
                        .substring(0, 4)
        );
    }
}
