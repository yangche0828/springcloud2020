package com.yangche.springcloud.controller;

import com.yangche.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@RequestMapping("/payment")
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @GetMapping("/hystrix/ok/{id}")
    public String paymentInfo(@PathVariable Integer id){
        String result = paymentService.paymentInfoOk(id);
        log.info("---------result:{}",result);
        return result;
    }

    @GetMapping("/hystrix/timeout/{id}")
    public String paymentTimeout(@PathVariable("id") Integer id){
        String result = paymentService.paymentInfoTimeout(id);
        log.info("---------result:{}",result);
        return result;
    }

    //=========服务熔断===========
    @GetMapping("/circuit/{id}")
    public String paymentCircuitBreaker(@PathVariable("id") Integer id){
        String result = paymentService.paymentCircuitBreaker(id);
        log.info("****result: "+result);
        return result;
    }
}
