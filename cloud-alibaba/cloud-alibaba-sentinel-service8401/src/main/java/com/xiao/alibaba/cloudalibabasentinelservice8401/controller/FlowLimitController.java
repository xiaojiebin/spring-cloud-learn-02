package com.xiao.alibaba.cloudalibabasentinelservice8401.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowItem;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRuleManager;
import com.xiao.alibaba.cloudalibabasentinelservice8401.exception.CustomizeBlockHandler;
import com.xiao.alibaba.cloudalibabasentinelservice8401.service.ConsumerService;
import com.xiao.cloud.cloudcommon.common.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author aloneMan
 * @projectName spring-cloud-learn-02
 * @createTime 2022-10-24 20:43:13
 * @description
 */

@RestController
@RequestMapping("/api/v1/")
@Slf4j
public class FlowLimitController {


    public final ConsumerService service;

    public FlowLimitController(ConsumerService consumerService) {
        this.service = consumerService;
    }

    @GetMapping("/get/{id}")
    public CommonResult getA(@PathVariable("id") Long id) {
        log.info("{} ", ">>>>>> 请求");
        return service.getPaymentById(id);
    }

    @GetMapping("/getTimeout")
    public CommonResult getB() throws InterruptedException {
        return service.getTimeout();
    }

    @GetMapping("/exception")
    public CommonResult exception() {
        return service.exception();
    }

    /**
     * 热点参数限流
     *
     * @param p1
     * @param p2
     * @return
     */
    @GetMapping("/HotKey")
    @SentinelResource(value = HOT_KEY, blockHandlerClass = CustomizeBlockHandler.class, blockHandler = "handlerMethod",
            fallbackClass = CustomizeBlockHandler.class, fallback = "fallbackMethod")
    public CommonResult hotKey(@RequestParam(name = "p1", required = false) String p1, @RequestParam(name = "p2", required = false) String p2) {
        int i = 1 / 0;
        return new CommonResult(0x00001L, "请求成功", "hot key request success > p1: " + p1 + " > p2 :" + p2);
    }

    @GetMapping("/sentinel/{id}")
    public CommonResult sentinel(@PathVariable("id") Long id) {
        return service.sentinel(id);
    }

    private final static String HOT_KEY = "testHotKey";


    @PostConstruct
    public void init() {
        //热点key设置
        //和HOT_KEY绑定的sentinelResource的限流规则是 每秒一个请求
        ParamFlowRule paramFlowRule = new ParamFlowRule(HOT_KEY)
                .setParamIdx(0)
                .setCount(1);
        //如果这个值是t5，则他每秒可以十个请求
        ParamFlowItem item = new ParamFlowItem().setObject(String.valueOf("t5")).setClassType(String.class.getName()).setCount(10);
        paramFlowRule.setParamFlowItemList(Collections.singletonList(item));
        ParamFlowRuleManager.loadRules(Collections.singletonList(paramFlowRule));
    }
}
