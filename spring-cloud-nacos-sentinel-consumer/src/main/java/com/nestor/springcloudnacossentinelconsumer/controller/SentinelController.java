package com.nestor.springcloudnacossentinelconsumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.nestor.springcloudnacossentinelconsumer.enums.InfoStatus;
import com.nestor.springcloudnacossentinelconsumer.producer.ProducerApi;
import com.nestor.springcloudnacossentinelconsumer.vo.Info;
import com.nestor.springcloudnacossentinelconsumer.vo.Result;

/**
 * @author : Nestor.Bian
 * @version : V 1.0
 * @date : 2021/5/24
 */
@RestController
@RequestMapping("/consumer")
public class SentinelController {

    @Autowired
    private ProducerApi producerApi;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/ribbon")
    public Result<String> test1() {
        Info info = new Info();
        info.setName("sentinel");
        info.setMessage("sentinel");
        info.setStatus(InfoStatus.SUCCESS);

        return restTemplate.postForObject("http://spring-cloud-nacos-sentinel-producer/producer/info", info,
                Result.class);
    }

    @GetMapping("/feign")
    public Result<String> test2() throws Throwable {
        Info info = new Info();
        info.setName("sentinel");
        info.setMessage("sentinel");
        info.setStatus(InfoStatus.SUCCESS);

        return producerApi.info(info);
    }


}
