package com.dev.superapp.config.http;


import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.time.Duration;

/****
 * this needs some improvement and coorections is not working on 2000 TPS
 */
@Configuration
public class PooledWebClientConfig {

    @Bean
    public WebClient pooledScalableWebClient (RestTemplateBuilder builder) {
        // Configure connection pool and timeout settings
        ConnectionProvider provider =
                ConnectionProvider.builder("custom")
                        .maxConnections(200)
                        .maxIdleTime(Duration.ofSeconds(100))
                        .maxLifeTime(Duration.ofSeconds(400))
                        .pendingAcquireTimeout(Duration.ofSeconds(200))
                        .evictInBackground(Duration.ofSeconds(200))
                        .build();

        HttpClient httpClient= HttpClient.create(provider);

        ClientHttpConnector connector = new ReactorClientHttpConnector(httpClient);

        return WebClient.builder()
                .clientConnector(connector)
                .build();
    }
}