package com.yangche.springcloud.lb;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
public class LoadBalanceImpl implements LoadBalance {

    private AtomicInteger atomicInteger = new AtomicInteger(0);

    /**
     * 自旋锁
     *
     * @return
     */
    public final int getAndIncrement() {
        int current;
        int next;
        do {
            current = this.atomicInteger.get();
            next = current >= 2147483647 ? 0 : current + 1;
        } while (!this.atomicInteger.compareAndSet(current, next));
        log.info("----------第{}次访问", next);
        return next;
    }

    /**
     * 负载均衡算法
     *
     * @param serviceInstances
     * @return
     */
    @Override
    public ServiceInstance instance(List<ServiceInstance> serviceInstances) {

        int index = getAndIncrement() % serviceInstances.size();
        return serviceInstances.get(index);
    }
}
