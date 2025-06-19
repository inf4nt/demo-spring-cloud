package demo.client.spring.cloud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ConditionalOnProperty(value = "spam.enabled", havingValue = "true")
public class SpamApiComponent {

    @Autowired
    private ApiFeignClient feignClient;

    @Value("${spam.timeout}")
    private String spamTimeout;

    @EventListener(ApplicationReadyEvent.class)
    public void listener() {
        log.info("SpamApiComponent is enabled. Spam timeout is {}", spamTimeout);
    }

    @Scheduled(fixedRateString = "${spam.timeout}")
    public void spam() {
        log.info("Spam api started");
        var api = feignClient.getApi();
        log.info("Spam api finished {}", api);
    }
}
