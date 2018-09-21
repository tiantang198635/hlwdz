package com.sign.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
public class JedisConfig {
	@Value("${spring.redis.host}")
    public String host;
    @Value("${spring.redis.port}")
    public int port;
    @Value("${spring.redis.database}")
    public int database;
    @Value("${spring.redis.pool.max-idle}")
    public int maxIdle;
    @Value("${spring.redis.pool.min-idle}")
    public int minIdle;
    @Value("${spring.redis.pool.max-active}")
    public int maxActive;
    @Value("${spring.redis.pool.max-wait}")
    public Integer maxWait;
    @Value("${spring.redis.timeout}")
    public String timeout;
    @Value("${spring.redis.password}")
    public String password;
}
