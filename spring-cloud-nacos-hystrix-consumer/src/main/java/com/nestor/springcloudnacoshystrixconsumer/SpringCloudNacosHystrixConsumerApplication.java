package com.nestor.springcloudnacoshystrixconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.nestor")
@EnableCircuitBreaker
public class SpringCloudNacosHystrixConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudNacosHystrixConsumerApplication.class, args);
    }

}
