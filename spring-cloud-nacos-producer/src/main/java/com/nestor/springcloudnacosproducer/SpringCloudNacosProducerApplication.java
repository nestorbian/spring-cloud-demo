package com.nestor.springcloudnacosproducer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SpringCloudNacosProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudNacosProducerApplication.class, args);
    }

}
