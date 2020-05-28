package com.yangche.springcloud.service;

import com.yangche.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Param;

public interface PaymentService {

    int create(Payment payment);

    Payment getPaymentById(Long id);
}
