package com.nestor.springcloudgatewaysentinelexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SpringCloudGatewaySentinelExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudGatewaySentinelExampleApplication.class, args);
    }

}
