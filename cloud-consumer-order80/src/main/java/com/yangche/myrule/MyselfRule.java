package com.yangche.myrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyselfRule {

    /**
     * 这个IRule是接口，ribbon的所有负载策略都是其子类
     *
     * @return
     */
    @Bean
    public IRule myRuleRandomRule() {
        return new RandomRule();//定义为随机
    }
}
