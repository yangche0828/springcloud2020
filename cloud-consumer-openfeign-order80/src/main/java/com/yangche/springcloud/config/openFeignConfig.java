package com.yangche.springcloud.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class openFeignConfig {

    /**
     * 要想改变打印openFeign的日志级别，需要将这个OpenFeign的日志交给spring管理
     * 同时需要在配置文件中进行配置
     * @return
     */
    @Bean
    Logger.Level feignLoggerLevel(){
        return Logger.Level.FULL;
    }
}
