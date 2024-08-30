package com.dev.superapp.controller;


import com.dev.superapp.dto.Item;
import com.dev.superapp.service.RestTemplateVsWebClient;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@Slf4j
@RequestMapping("/api-test")
@RestController
public class BlockingAndNonBlockingAPIController {

    @Setter(onMethod_={@Autowired})    // setter injection is used
    private RestTemplateVsWebClient restTemplateVsWebClient;

    @GetMapping("/blocking")
    public List<Item> fetchItemsBlocking() {
        List<Item> list =   restTemplateVsWebClient.sendBlockingRequest().getBody();
        list.forEach(item -> log.info(item.toString()));
        log.info("Exiting BLOCKING Controller!");
        return list;
    }

    @GetMapping(value = "/non-blocking",
            produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Item> fetchItemsNonBlocking() {
        return restTemplateVsWebClient.sendNonBlockingRequest();
    }


}
