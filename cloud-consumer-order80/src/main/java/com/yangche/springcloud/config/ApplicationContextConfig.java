package com.yangche.springcloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationContextConfig {

    @Bean
    //使用loadBalanced注解赋予RestTemplate负载均衡的能力
//    @LoadBalanced //注释掉，因为要用自己写的负载均衡算法
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
