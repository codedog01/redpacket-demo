package com.lengao.seckill.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;


/**
 * <p>description goes here</p>
 *
 * @author 冷澳
 * @date 2022/12/28
 */

@Configuration
@EnableCaching
@Slf4j
public class JedisConfig extends CachingConfigurerSupport {

    @Value("${spring.redis.host:121.43.163.177}")
    private String host;

    @Value("${spring.redis.port:6277}")
    private int port;

    @Value("${spring.redis.timeout:2000}")
    private int timeout;

    @Value("${spring.redis.jedis.pool.max-active:200}")
    private int maxActive;

    @Value("${spring.redis.jedis.pool.max-wait:-1}")
    private int maxWait;

    @Value("${spring.redis.jedis.pool.max-idle:10}")
    private int maxIdle;

    @Value("${spring.redis.jedis.pool.min-idle:10}")
    private int minIdle;

    @Bean
    public JedisSentinelPool jedisPool() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMinIdle(minIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWait);
        jedisPoolConfig.setMaxTotal(maxActive);
//        JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, null);
//        log.info("注册ReidsPool成功：redis地址 {} ，端口号 {} ", host, port);
        HashSet<String> sentinels = new HashSet<>();
        sentinels.add("121.43.163.177:26379");
        sentinels.add("121.43.163.177:26380");
        sentinels.add("121.43.163.177:26381");
        JedisSentinelPool sentinelPool = new JedisSentinelPool("mymaster", sentinels, jedisPoolConfig, "root123.");
        log.info("注册SentinelPool成功，sentinel节点{}", sentinels);
        return sentinelPool;
    }
}
