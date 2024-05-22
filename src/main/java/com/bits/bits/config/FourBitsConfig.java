package com.bits.bits.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class FourBitsConfig {

    @Value("${spring.redis.host}")
    private String springRedisHost;

    @Value("${spring.redis.port}")
    private int springRedisPort;
}
