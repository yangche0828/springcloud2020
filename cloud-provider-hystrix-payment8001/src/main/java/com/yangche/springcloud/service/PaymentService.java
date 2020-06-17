package com.yangche.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {

    public String paymentInfoOk(Integer id) {
        return "线程池" + Thread.currentThread().getName() + "\tpayment ok ,id:" + id + "o(∩_∩)o 哈哈。。。";
    }

    /**
     * fallbackMethod
     * 对应的值为当本方法出现异常或超时等问题时去调用哪个方法，一个兜底的降级的方案
     * HystrixProperty
     * 对应的值为 execution.isolation.thread.timeoutInMilliseconds 设置降级时间限制
     */
    @HystrixCommand(
            fallbackMethod = "paymentInfoTimeoutHandler",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")
            }
    )
    public String paymentInfoTimeout(Integer id) {
        //情况1：当程序中出现异常时，会马上转到降级执行方法
//        int i=10/0;
        //情况2：当代码的执行时间超过设置的熔断时间也会马上执行降级的方法上
        long time = 3000;
        try {
            TimeUnit.MILLISECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "【服务方，线程池" + Thread.currentThread().getName() + "\tpayment timeout ,id:" + id + "\t耗时：" + time + "毫秒】";
    }

    /**
     * 当服务器出现异常货超时等问题发生时，hystrixhi调用此方法作为托底的服务降级的执行返回方案
     *
     * @param id
     * @return
     */
    public String paymentInfoTimeoutHandler(Integer id) {
        return "我是服务方，线程池:  " + Thread.currentThread().getName() + "  8001系统繁忙或者运行报错，请稍后再试,id:  " + id + "\t" + "o(╥﹏╥)o";
    }


    //===========服务熔断====================
    //所有的HystrixProperty都可以在类HystrixCommandProperties找到
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),// 是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),// 请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"), // 时间窗口期
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60"),// 失败率达到多少后跳闸
    })
    public String paymentCircuitBreaker(Integer id) {
        if (id < 0) {
            throw new RuntimeException("******id 不能负数");
        }
        String serialNumber = IdUtil.simpleUUID();

        return Thread.currentThread().getName() + "\t" + "调用成功，流水号: " + serialNumber;
    }

    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id) {
        return "id 不能负数，请稍后再试，/(ㄒoㄒ)/~~   id: " + id;
    }
}
