package com.qyc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author qyc
 * @time 2020/11/15 - 12:13
 */
@SpringBootApplication
@MapperScan("com.qyc.mapper")
public class Main5001 {
    public static void main(String[] args) {
        SpringApplication.run(Main5001.class,args);
    }
}
