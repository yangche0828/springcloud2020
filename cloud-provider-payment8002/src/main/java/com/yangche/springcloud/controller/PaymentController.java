package com.yangche.springcloud.controller;

import com.yangche.springcloud.entities.CommonResult;
import com.yangche.springcloud.entities.Payment;
import com.yangche.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;
    //将配置文件中的server.port导入进来，只是为了查看调用的是哪个服务
    @Value("${server.port}")
    private String serverPort;

    @PostMapping("/create")
    public CommonResult create(@RequestBody Payment payment) {
        int result = paymentService.create(payment);
        log.info("------插入结果：{}",result);
        if(result>0){
            return new CommonResult(200, "插入数据库成功,serverPort:"+serverPort, result);
        }else{
            return new CommonResult(444, "插入数据失败");
        }
    }

    @GetMapping("/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id) {
        Payment paymentById = paymentService.getPaymentById(id);
        log.info("------查询结果：{}",paymentById);
        if(paymentById!=null){
            return new CommonResult(200, "查询数据库成功,serverPort:"+serverPort, paymentById);
        }else{
            return new CommonResult(444, "查询数据失败");
        }
    }

    /**
     * 获取端口
     * @return
     */
    @GetMapping(value = "/lb")
    public String getPaymentLB(){
        return serverPort;
    }

    /**
     * 故意设置延时，模拟复杂业务
     * openfeign的默认超时时间是1秒，如果超过一秒就会报超时的错误，这个就是为了演示这个
     * @return
     */
    @GetMapping(value = "/openfeign/timeout")
    public String paymentOpenFeignTimeout(){
        try {
            TimeUnit.SECONDS.sleep(3l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return serverPort;
    }

}
