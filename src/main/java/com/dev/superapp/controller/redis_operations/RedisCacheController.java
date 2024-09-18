package com.dev.superapp.controller.redis_operations;

import com.dev.superapp.service.redis.CacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/redis-cache")
@RequiredArgsConstructor
public class RedisCacheController {

    private final CacheService cacheService;

    @GetMapping("")
    public String fetchValueForKey(@RequestParam String key){
        return  cacheService.getValue(key);
    }

    @PostMapping("")
    public void saveKeyValue(@RequestParam String key,@RequestParam String value){
        cacheService.saveValueWithExpiryTimeInMinutes(key,value,10);
    }

}
