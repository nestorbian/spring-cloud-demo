package com.nestor.springcloudnacoshystrixconsumer.producer;

import com.nestor.springcloudnacoshystrixconsumer.common.HystrixProducerFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.nestor.springcloudnacoshystrixconsumer.common.HystrixProducerFallbackFactory;
import com.nestor.springcloudnacoshystrixconsumer.vo.Info;
import com.nestor.springcloudnacoshystrixconsumer.vo.Result;

@FeignClient(name = "${producer.service.name:producer}", path = "/producer", fallback = HystrixProducerFallBack.class/*, fallbackFactory = HystrixProducerFallbackFactory.class*/)
public interface ProducerApi {

    @GetMapping(path = "/info")
    String info();

    @PostMapping(path = "/info")
    Result<String> info(@RequestBody Info info) throws Throwable;

}
