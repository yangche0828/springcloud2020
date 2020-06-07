package com.yangche.springcloud.controller;

import com.yangche.springcloud.entities.CommonResult;
import com.yangche.springcloud.entities.Payment;
import com.yangche.springcloud.service.PaymentOpenFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@RequestMapping("/consumer")
public class OrderOpenFeignController {

    @Resource
    private PaymentOpenFeignService paymentOpenFeignService;

    @GetMapping(value = "/payment/get/{id}")
    public CommonResult<Payment> getPaymentId(@PathVariable("id") Long id){
        CommonResult paymentById = paymentOpenFeignService.getPaymentById(id);
        return paymentById;
    }

    @GetMapping(value = "/payment/openfeign/timeout")
    public String openFeignTimeout(){
        String serverPort = paymentOpenFeignService.paymentOpenFeignTimeout();
        return serverPort;
    }

}
