package com.qyc.cfg;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author qyc
 * @time 2020/7/2 - 22:56
 */
@Configuration
public class PageCfg implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/ossup").setViewName("osspage");
        registry.addViewController("/in").setViewName("index");
    }
}
