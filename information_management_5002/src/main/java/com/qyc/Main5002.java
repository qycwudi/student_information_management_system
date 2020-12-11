package com.qyc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author qyc
 * @time 2020/11/15 - 12:13
 */
@SpringBootApplication
@MapperScan("com.qyc.mapper")
@EnableCaching
@EnableDiscoveryClient
@EnableFeignClients
public class Main5002 {
    public static void main(String[] args) {
        SpringApplication.run(Main5002.class,args);
    }
}
