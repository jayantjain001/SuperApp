package com.dev.superapp.service.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class CacheService {

    private final RedisTemplate<String, Object> redisTemplate;

    public void saveValue(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void saveValueWithExpiryTimeInMinutes(String key, String value, int minutes) {
        redisTemplate.opsForValue().set(key, value,Duration.ofMinutes(minutes));
    }

    public String getValue(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    public void deleteValue(String key) {
        redisTemplate.delete(key);
    }


}
