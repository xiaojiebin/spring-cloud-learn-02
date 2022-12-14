package com.xiao.cloud.cloudhystrixproviderpayment8001;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@SpringBootApplication
@EnableEurekaClient
@EnableHystrix
public class CloudHystrixProviderPayment8001Application {

    public static void main(String[] args) {
        SpringApplication.run(CloudHystrixProviderPayment8001Application.class, args);
    }

}
