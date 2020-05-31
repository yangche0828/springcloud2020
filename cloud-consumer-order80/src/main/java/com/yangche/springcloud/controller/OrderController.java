package com.yangche.springcloud.controller;

import com.yangche.springcloud.entities.CommonResult;
import com.yangche.springcloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@Slf4j
@RequestMapping("/consumer")
public class OrderController {
//    public static final String PAYMENT_URL = "http://localhost:8001";
    @Value("${payment.url}")
    private String paymentUrl;

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/payment/create")
    public CommonResult<Payment> create(Payment payment) {
        log.info("客户调用创建参数为{}",payment);
        CommonResult commonResult = restTemplate.postForObject(paymentUrl + "/payment/create", payment, CommonResult.class);
        return commonResult;
    }

    @GetMapping("/payment/get/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id") String id){
        log.info("客户调用获取订单，订单id为{}",id);
        CommonResult forObject = restTemplate.getForObject(paymentUrl + "/payment/get/" + id, CommonResult.class);
        return forObject;

    }

}
