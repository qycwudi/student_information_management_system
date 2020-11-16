package com.qyc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author qyc
 * @time 2020/8/4 - 14:13
 */
@SpringBootApplication
@MapperScan({"com.qyc.mapper","com.qyc.plu.mapper"})
public class MainQx {
    public static void main(String[] args) {
        SpringApplication.run(MainQx.class,args);
    }
}
