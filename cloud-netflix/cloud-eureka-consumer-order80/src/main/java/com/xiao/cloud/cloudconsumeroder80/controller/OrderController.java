package com.xiao.cloud.cloudconsumeroder80.controller;

import com.xiao.cloud.cloudcommon.common.CommonResult;
import com.xiao.cloud.cloudcommon.entity.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * @author aloneMan
 * @projectName spring-cloud-learn-02
 * @createTime 2022-10-13 23:50:11
 * @description
 */
@RestController
@RequestMapping("/consumer/order")
@Slf4j
public class OrderController {

    private static final String PAYMENT_URL = "http://PROVIDER-PAYMENT-SERVICE";
    private final RestTemplate restTemplate;



    public OrderController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/get/{id}")
    public CommonResult<Payment> selectById(@PathVariable("id") Long id) {
        return restTemplate.getForObject(PAYMENT_URL + "/provider/payment/get/" + id, CommonResult.class);
    }

    @PostMapping("/add")
    public CommonResult<Payment> addPayment(Payment payment) {
        return restTemplate.postForObject(PAYMENT_URL + "/provider/payment/add", payment, CommonResult.class);
    }

    @GetMapping("/payment/zipkin")
    public String paymentZipkin() {
        return "hi ,i'am paymentzipkin server fall back，welcome to here, O(∩_∩)O哈哈~";
    }
}
