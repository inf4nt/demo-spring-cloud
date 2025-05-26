package com.example.demo.client.spring.cloud.democlientspringcloud;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
        name = "${api.service.name}",
        path = "/api",
        dismiss404 = true,
        url = "${api.service.url:}"
        // application can run in two modes. the first using eureka and the second one k8s
        // For eureka mode feign client requires name. Feign gets api url from loadbalancer based on given name, so url can be null.
        // For k8s mode feign client requires URL. k8s sets api URL. name can be any value
)
public interface ApiFeignClient {

    @GetMapping
    ResponseEntity<?> getApi();
}
