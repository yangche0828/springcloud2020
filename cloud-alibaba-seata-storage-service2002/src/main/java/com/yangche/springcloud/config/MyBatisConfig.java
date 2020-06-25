package com.yangche.springcloud.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan({"com.yangche.springcloud.dao"})
public class MyBatisConfig {
}
