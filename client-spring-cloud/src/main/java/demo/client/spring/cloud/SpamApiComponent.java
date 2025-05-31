package demo.client.spring.cloud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ConditionalOnProperty(value = "spam.enabled", havingValue = "true")
public class SpamApiComponent {

    @Autowired
    private ApiFeignClient feignClient;

    @Scheduled(fixedRateString = "${spam.timeout}")
    public void spam() {
        log.info("Spam api started");
        Object api = feignClient.getApi();
        log.info("Spam api finished {}", api);
    }
}
