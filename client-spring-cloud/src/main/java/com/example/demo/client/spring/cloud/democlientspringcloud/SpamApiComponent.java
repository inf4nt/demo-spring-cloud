package com.example.demo.client.spring.cloud.democlientspringcloud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@ConditionalOnProperty(value = "api.spam.enabled", havingValue = "true")
@Slf4j
public class SpamApiComponent {

    @Autowired
    private ApiFeignClient feignClient;

    @Scheduled(fixedRate = 1000, initialDelay = 30000)
    public void spam() {
        log.info("Spam api started");
        ApiFeignClient.ApiResponse<?> api = feignClient.getApi();
        log.info("Spam api finished {}", api);
    }
}
