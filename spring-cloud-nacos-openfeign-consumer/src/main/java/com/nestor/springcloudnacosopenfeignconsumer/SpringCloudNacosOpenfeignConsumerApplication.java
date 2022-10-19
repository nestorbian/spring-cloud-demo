package com.nestor.springcloudnacosopenfeignconsumer;

import com.nestor.springcloudnacosopenfeignconsumer.support.CommonPropertySourceFactory;
import feign.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.nestor")
@PropertySource(value = "classpath:/extension-config.yml", factory = CommonPropertySourceFactory.class)
public class SpringCloudNacosOpenfeignConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudNacosOpenfeignConsumerApplication.class, args);
    }

}
