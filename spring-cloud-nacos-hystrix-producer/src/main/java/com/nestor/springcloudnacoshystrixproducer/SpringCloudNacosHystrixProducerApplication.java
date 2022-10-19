package com.nestor.springcloudnacoshystrixproducer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

@SpringBootApplication
@EnableCircuitBreaker
public class SpringCloudNacosHystrixProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudNacosHystrixProducerApplication.class, args);
    }

}
