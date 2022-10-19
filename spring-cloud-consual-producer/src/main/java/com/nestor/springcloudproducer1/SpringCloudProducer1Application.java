package com.nestor.springcloudproducer1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableDiscoveryClient
@EnableScheduling  //启用后，会定时拉取配置
public class SpringCloudProducer1Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudProducer1Application.class, args);
    }

}
