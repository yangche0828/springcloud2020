package com.yangche.springcloud.controller;

import com.yangche.springcloud.entities.CommonResult;
import com.yangche.springcloud.entities.Payment;
import com.yangche.springcloud.lb.LoadBalance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/consumer")
public class OrderController {
    //    public static final String PAYMENT_URL = "http://localhost:8001";
    @Value("${payment.url}")
    private String paymentUrl;

    @Resource
    private RestTemplate restTemplate;
    @Resource
    private LoadBalance loadBalance;
    @Resource
    private DiscoveryClient discoveryClient;

    @GetMapping("/payment/create")
    public CommonResult<Payment> create(Payment payment) {
        log.info("客户调用创建参数为{}", payment);
        CommonResult commonResult = restTemplate.postForObject(paymentUrl + "/payment/create", payment, CommonResult.class);
        return commonResult;
    }

    @GetMapping("/payment/get/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id") String id) {
        log.info("客户调用获取订单，订单id为{}", id);
        CommonResult forObject = restTemplate.getForObject(paymentUrl + "/payment/get/" + id, CommonResult.class);
        return forObject;

    }

    /**
     * 获取entity方法试验
     *
     * @param id
     * @return
     */
    @GetMapping("/payment/entity/{id}")
    public CommonResult getEntityPayment(@PathVariable("id") String id) {
        ResponseEntity<CommonResult> forEntity = restTemplate.getForEntity(paymentUrl + "/payment/get/" + id, CommonResult.class);
        int statusCodeValue = forEntity.getStatusCodeValue();
        if (forEntity.getStatusCode().is2xxSuccessful()) {
            log.info("状态码为：{}", statusCodeValue);
            return new CommonResult(statusCodeValue, "consumer entity 调用成功");
        } else {
            return new CommonResult(statusCodeValue, "consumer entity 调用失败");
        }
    }

    @GetMapping("/payment/lb")
    public String getPaymentLB() {
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        if (instances == null || instances.size() <= 0) {
            return "未发现服务";
        }
        ServiceInstance instance = loadBalance.instance(instances);
        URI uri = instance.getUri();
        return restTemplate.getForObject(uri + "/payment/lb", String.class);
    }


}
