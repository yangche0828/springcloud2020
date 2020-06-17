package com.yangche.springcloud.service;

import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements PaymentHystrixService {

    @Override
    public String paymentInfo(Integer id) {
        return "[-----------PaymentFallbackService,看个der,我是自定义的为接口paymentInfo默认降级处理类(┬＿┬)]";

    }

    @Override
    public String paymentTimeout(Integer id) {
        return "[----------PaymentFallbackService，笑个der，我是接口paymentTimeout的服务降级处理类(┬＿┬)]";
    }

    @Override
    public String paymentCircuitBreaker(Integer id) {
        return null;
    }
}
