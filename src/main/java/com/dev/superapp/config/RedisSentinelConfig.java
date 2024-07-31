package com.dev.superapp.config;


import com.dev.superapp.constants.RedisConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

@Configuration
public class RedisSentinelConfig {

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration()
                .master(RedisConstants.MY_MASTER)                // The name of the master node as configured in Sentinel
                .sentinel(RedisConstants.LOCALHOST, 26379) // Sentinel host and port
                .sentinel(RedisConstants.LOCALHOST, 26380) // Another Sentinel host and port if applicable
                .sentinel(RedisConstants.LOCALHOST, 26381); // Another Sentinel host and port if applicable

        return new JedisConnectionFactory(sentinelConfig);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        template.setValueSerializer(new GenericToStringSerializer<>(Object.class));
        return template;
    }
}
