package com.yangche.springcloud.service;

import com.yangche.springcloud.entities.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//不需要加这个注解也行
@Component
//告诉去找eureka的微服务名称是什么
@FeignClient(value = "CLOUD-PAYMENT-SERVICE")
public interface PaymentOpenFeignService {

    @GetMapping("/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id);

    /**
     * 模拟超时的情况
     * @return
     */
    @GetMapping(value = "/payment/openfeign/timeout")
    public String paymentOpenFeignTimeout();
}
