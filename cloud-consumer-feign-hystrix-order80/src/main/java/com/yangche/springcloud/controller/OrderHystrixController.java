package com.yangche.springcloud.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.yangche.springcloud.service.PaymentHystrixService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@RequestMapping("/consumer")
@DefaultProperties(defaultFallback = "paymentGlobalFallbackMethod")
public class OrderHystrixController {

    @Resource
    private PaymentHystrixService paymentHystrixService;

    @GetMapping("/payment/hystrix/ok/{id}")
    @HystrixCommand
    public String paymentHystrixInfo(@PathVariable Integer id){
        String s = paymentHystrixService.paymentInfo(id);
        int i=2/0;
        log.info("*****************请求结果为：{}",s);
        return s;
    }


    @HystrixCommand(fallbackMethod = "paymentHystrixTimeoutHandler",
            commandProperties = {
                    @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="5000")
            }
    )
    @GetMapping("/payment/hystrix/timeout/{id}")
    public String paymentHystrixTimeout(@PathVariable("id") Integer id){
//        int i=10/0;
        String s = paymentHystrixService.paymentTimeout(id);
        log.info("*************请求结果为：{}",s);
        return s;
    }

    public String paymentHystrixTimeoutHandler(Integer id){
        String s = "我是消费方，传入id为："+id+",80系统繁忙或者运行报错，请稍后再试o(╥﹏╥)o";
        return s;
    }

    /**
     * 下面是全局的fallback方法
     * 如果有自定义的fullback用自定义的，没有就用全局的，用这个全局的需要多配置两个地方
     * 1.类上面用@DefaultProperties(defaultFallback = "paymentGlobalFallbackMethod")指定全局的fullback方法
     * 2.在需要用的可能要用到降级的方法上添加注解@HystrixCommand
     * @return
     */
    public String paymentGlobalFallbackMethod(){
        return "[全局的fallback]";
    }
}
