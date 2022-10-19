package com.nestor.springcloudproducer1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
@RequestMapping(path = "/producer1")
public class ProducerController {

    @Value("${testName:}")
    private String testName;

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/info")
    public String info(@RequestParam("name") String name) {
        System.err.println(String.format("[%s] name is %s", System.currentTimeMillis(), name));
        return String.format("producer1: [%s]", testName);
    }

    @GetMapping("/error")
    public String error() {
        System.err.println("producer1 occurs error");
        throw new RuntimeException("发生错误");
    }

    @GetMapping("/discovery")
    public String discovery() {
        System.err.println(discoveryClient.getInstances("spring-cloud-producer1"));
        return "SUCCESS";
    }

}
