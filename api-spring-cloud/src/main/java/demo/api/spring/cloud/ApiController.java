package demo.api.spring.cloud;

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
                "value", "Api is running. Call /api to work with it."
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
