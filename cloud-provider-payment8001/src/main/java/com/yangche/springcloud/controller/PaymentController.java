package com.yangche.springcloud.controller;

import com.yangche.springcloud.entities.CommonResult;
import com.yangche.springcloud.entities.Payment;
import com.yangche.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/payment/create")
    public CommonResult create(@RequestBody Payment payment) {
        int result = paymentService.create(payment);
        log.info("------插入结果：{}",result);
        if(result>0){
            return new CommonResult(200, "插入数据库成功", result);
        }else{
            return new CommonResult(444, "插入数据失败");
        }
    }

    @GetMapping("/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id) {
        Payment paymentById = paymentService.getPaymentById(id);
        log.info("------查询结果：{}",paymentById);
        if(paymentById!=null){
            return new CommonResult(200, "查询数据库成功", paymentById);
        }else{
            return new CommonResult(444, "查询数据失败");
        }
    }

}
