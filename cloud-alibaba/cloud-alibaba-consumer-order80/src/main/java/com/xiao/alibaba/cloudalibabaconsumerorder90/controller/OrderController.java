package com.xiao.alibaba.cloudalibabaconsumerorder90.controller;

import com.xiao.alibaba.cloudalibabaconsumerorder90.service.PaymentService;
import com.xiao.cloud.cloudcommon.common.CommonResult;
import com.xiao.cloud.cloudcommon.entity.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author aloneMan
 * @projectName spring-cloud-learn-02
 * @createTime 2022-10-13 23:50:11
 * @description
 */
@RestController
@RequestMapping("/openfeign/consumer/order")
@Slf4j
public class OrderController {


    private final PaymentService paymentService;

    public OrderController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }


    @GetMapping("/get/{id}")
    public CommonResult<Payment> selectById(@PathVariable("id") Long id) {
        return paymentService.getPaymentById(id);
    }

    @PostMapping("/add")
    public CommonResult<Payment> addPayment(Payment payment) {
        return paymentService.addPayment(payment);
    }

    @GetMapping("/getTimeout")
    public void getTimeout() {
        paymentService.getTimeout();
    }

}
