package com.yangche.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationContextBean {

    /**
     * 对于第三方库的bean，如果想被spring进行管理
     * 需要这个配置bean，或者在xml中进行配置用bean标签
     * @return
     */
    @Bean
    @LoadBalanced//负载均衡的注解
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

}
