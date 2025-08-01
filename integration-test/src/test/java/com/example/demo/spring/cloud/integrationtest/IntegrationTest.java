package com.example.demo.spring.cloud.integrationtest;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.time.Duration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class IntegrationTest {

    private static GenericContainer<?> EUREKA_CONTAINER;

    private static GenericContainer<?> API_CONTAINER;

    private static GenericContainer<?> CLIENT_CONTAINER;

    @SuppressWarnings("resource")
    @BeforeAll
    public static void before() {
        EUREKA_CONTAINER = new GenericContainer<>("eureka-spring-cloud")
                .withExposedPorts(8080)
                .withEnv("SERVER_PORT", "8080")
                .withNetwork(Network.SHARED)
                .withNetworkAliases("eureka");
        EUREKA_CONTAINER.start();

        API_CONTAINER = new GenericContainer<>("api-spring-cloud")
                .withExposedPorts(8080)
                .withNetwork(Network.SHARED)
                .withEnv("SERVER_PORT", "8080")
                .withEnv("SPRING_PROFILES_ACTIVE", "production")
                .withEnv("SPRING_APPLICATION_NAME", "api-spring-cloud-spring-application-name")
                .withEnv("DISCOVERY_SERVER_URL", "http://eureka:8080/eureka")
                .dependsOn(EUREKA_CONTAINER);
        API_CONTAINER.start();

        CLIENT_CONTAINER = new GenericContainer<>("client-spring-cloud")
                .withExposedPorts(8080)
                .withNetwork(Network.SHARED)
                .withEnv("SERVER_PORT", "8080")
                .withEnv("SPAM_ENABLED", "false")
                .withEnv("SPRING_PROFILES_ACTIVE", "production")
                .withEnv("SPRING_APPLICATION_NAME", "client-spring-cloud-spring-application-name")
                .withEnv("DISCOVERY_SERVER_URL", "http://eureka:8080/eureka")
                .dependsOn(EUREKA_CONTAINER, API_CONTAINER);
        CLIENT_CONTAINER.start();

        waitUntilApplicationAndClientRegistersInEureka();
    }

    @AfterAll
    public static void after() {
        if (CLIENT_CONTAINER != null) {
            CLIENT_CONTAINER.stop();
        }
        if (API_CONTAINER != null) {
            API_CONTAINER.stop();
        }
        if (EUREKA_CONTAINER != null) {
            EUREKA_CONTAINER.stop();
        }
    }

    private static void waitUntilApplicationAndClientRegistersInEureka() {
        Awaitility.await()
                .timeout(Duration.ofSeconds(60))
                .ignoreExceptions()
                .pollInterval(Duration.ofSeconds(1))
                .logging(__ -> System.out.println("Waiting for applications to be available in the Eureka"))
                .untilAsserted(() -> {
                    String eurekaUrl = "http://" + EUREKA_CONTAINER.getHost() + ":" + EUREKA_CONTAINER.getFirstMappedPort();
                    String eurekaAppsUrl = eurekaUrl + "/eureka/apps";

                    HttpURLConnection httpURLConnection = (HttpURLConnection) URI
                            .create(eurekaAppsUrl + "/api-spring-cloud-spring-application-name")
                            .toURL()
                            .openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    assertThat(httpURLConnection.getResponseCode(), is(200));

                    httpURLConnection = (HttpURLConnection) URI
                            .create(eurekaAppsUrl + "/client-spring-cloud-spring-application-name")
                            .toURL()
                            .openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    assertThat(httpURLConnection.getResponseCode(), is(200));
                });
    }

    @Test
    public void apiRoot() throws Exception {
        String clientBaseUrl = "http://" + API_CONTAINER.getHost() + ":" + API_CONTAINER.getFirstMappedPort();
        HttpURLConnection connection = (HttpURLConnection) new URL(clientBaseUrl).openConnection();
        connection.setRequestMethod("GET");

        assertThat(connection.getResponseCode(), is(200));
    }

    @Test
    public void apiApi() throws Exception {
        String clientBaseUrl = "http://" + API_CONTAINER.getHost() + ":" + API_CONTAINER.getFirstMappedPort();
        HttpURLConnection connection = (HttpURLConnection) new URL(clientBaseUrl + "/api").openConnection();
        connection.setRequestMethod("GET");

        assertThat(connection.getResponseCode(), is(200));
    }

    @Test
    public void clientRoot() throws Exception {
        String clientBaseUrl = "http://" + CLIENT_CONTAINER.getHost() + ":" + CLIENT_CONTAINER.getFirstMappedPort();
        HttpURLConnection connection = (HttpURLConnection) new URL(clientBaseUrl).openConnection();
        connection.setRequestMethod("GET");

        assertThat(connection.getResponseCode(), is(200));
    }

    @Test
    public void clientClient() {
        Awaitility
                .await()
                .timeout(Duration.ofSeconds(60))
                .pollInterval(Duration.ofSeconds(1))
                .ignoreExceptions()
                .logging(__ -> System.out.println("Retrying /client"))
                .untilAsserted(() -> {
                    String clientBaseUrl = "http://" + CLIENT_CONTAINER.getHost() + ":" + CLIENT_CONTAINER.getFirstMappedPort();
                    HttpURLConnection connection = (HttpURLConnection) new URL(clientBaseUrl + "/client").openConnection();
                    connection.setRequestMethod("GET");

                    assertThat(connection.getResponseCode(), is(200));
                });
    }
}
