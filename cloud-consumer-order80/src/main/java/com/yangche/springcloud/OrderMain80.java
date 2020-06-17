package com.yangche.springcloud;

import com.yangche.myrule.MyselfRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

/**
 * @SpringBootApplication注解中存在一个子注解@ComponentScan,如果用自定义rubbion规则，就不能让这个注解扫描到，具体可以看官方文档 而这个注解可以扫描的范围是同一级包以及子包，所以springcloud文件夹下的都能被扫描到，用自定义规则需要在yangche文件夹下新建一个包
 */
@SpringBootApplication
//注册进eureka
@EnableEurekaClient
//这个的作用是告诉ribbon用自定义的负载策略，注意服务名字是大写的，eureka默认转换为了大写
@RibbonClient(name = "CLOUD-PAYMENT-SERVICE", configuration = MyselfRule.class)
public class OrderMain80 {
    public static void main(String[] args) {
        SpringApplication.run(OrderMain80.class, args);
    }
}
