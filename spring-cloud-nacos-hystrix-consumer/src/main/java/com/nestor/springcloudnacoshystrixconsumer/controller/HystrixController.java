package com.nestor.springcloudnacoshystrixconsumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nestor.springcloudnacoshystrixconsumer.enums.InfoStatus;
import com.nestor.springcloudnacoshystrixconsumer.producer.ProducerApi;
import com.nestor.springcloudnacoshystrixconsumer.vo.Info;
import com.nestor.springcloudnacoshystrixconsumer.vo.Result;

/**
 * @author : Nestor.Bian
 * @version : V 1.0
 * @date : 2021/5/24
 */
@RestController
@RequestMapping("/consumer")
public class HystrixController {

    @Autowired
    private ProducerApi producerApi;

    @GetMapping("/feign")
    public Result<String> test2() throws Throwable {
        Info info = new Info();
        info.setName("hystrix");
        info.setMessage("hystrix");
        info.setStatus(InfoStatus.SUCCESS);

        return producerApi.info(info);
    }

    @GetMapping("/info")
    public String test1() throws Throwable {
        return producerApi.info();
    }


}
