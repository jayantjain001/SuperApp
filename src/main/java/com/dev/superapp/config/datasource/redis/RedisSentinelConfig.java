package com.dev.superapp.config.datasource.redis;


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

    /** min of 3 sentinel and 3 redis nodes are used in prod
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration()
                .master(RedisConstants.MY_MASTER)
                .sentinel(RedisConstants.LOCALHOST, 26379);
              /*  .sentinel(RedisConstants.LOCALHOST, 26380)
                .sentinel(RedisConstants.LOCALHOST, 26381); */

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
