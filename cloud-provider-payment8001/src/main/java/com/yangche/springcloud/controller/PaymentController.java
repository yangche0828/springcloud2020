package com.yangche.springcloud.controller;

import com.yangche.springcloud.entities.CommonResult;
import com.yangche.springcloud.entities.Payment;
import com.yangche.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;
    //将配置文件中的server.port导入进来，只是为了查看调用的是哪个服务
    @Value("${server.port}")
    private String serverPort;

    @Resource
    private DiscoveryClient discoveryClient;

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

    @GetMapping("/discovery")
    public Object paymentDiscovery(){
        List<String> services = discoveryClient.getServices();
        //服务发现，获取所有的服务
        services.forEach(element->{
            log.info("*******element:{}",element);
        });
        //获取所有对应的实例
        List<ServiceInstance> instances = discoveryClient.getInstances("cloud-payment-service");
        instances.forEach(instance->{
            log.info(instance.getServiceId()+"\t"+instance.getHost()+"\t"+instance.getPort()+"\t"+instance.getUri());
        });
        return this.discoveryClient;
    }

    /**
     * 获取端口
     * @return
     */
    @GetMapping(value = "/lb")
    public String getPaymentLB(){
        return serverPort;
    }



}
