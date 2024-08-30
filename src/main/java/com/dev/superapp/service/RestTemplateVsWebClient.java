package com.dev.superapp.service;

import com.dev.superapp.dto.Item;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;

import java.net.URI;
import java.time.Duration;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestTemplateVsWebClient {

   @Qualifier("scalableRestTemplate")
   private final RestTemplate restTemplate;

    @Qualifier("pooledScalableWebClient")
    private final WebClient pooledScalableWebClient;

    private static final  String OTHER_SERVICE_URL = "http://localhost:8081/heavy-api";

    public ResponseEntity<List<Item>> sendBlockingRequest(){
        String url = UriComponentsBuilder.fromHttpUrl(OTHER_SERVICE_URL)
         //       .queryParam("param1", "value1")                             Request Param
                  .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        // headers.set("Authorization", "Bearer your_access_token");         Request Headers

        HttpEntity<Object> entity = new HttpEntity<>(headers);

        return restTemplate.exchange( url,  HttpMethod.GET,  entity,new ParameterizedTypeReference<List<Item>>(){});
    }

    @SneakyThrows
    public Flux<Item> sendNonBlockingRequest() {

        log.info("Starting NON-BLOCKING Controller! API1");

        URI uri =new URI(OTHER_SERVICE_URL);

        Flux<Item> itemFlux = WebClient.create()
                .get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(Item.class);

        itemFlux.timeout(Duration.ofSeconds(300)).subscribe(item -> {
//            log.info("received 1 item via async subscription : {}",item.toString());
        });

        log.info("Exiting NON-BLOCKING Controller! API 1");
        return itemFlux;
    }

    @SneakyThrows
    public Flux<Item> sendNonBlockingRequest1() {

        log.info("Starting NON-BLOCKING Controller! API2");

        URI uri =new URI(OTHER_SERVICE_URL);

        Flux<Item> itemFlux = pooledScalableWebClient
                .get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(Item.class);

        itemFlux.timeout(Duration.ofSeconds(300)).subscribe(item -> {
//            log.info("received 1 item via async subscription : {}",item.toString());
        });

        log.info("Exiting NON-BLOCKING Controller! API 2");
        return itemFlux;
    }


    }
