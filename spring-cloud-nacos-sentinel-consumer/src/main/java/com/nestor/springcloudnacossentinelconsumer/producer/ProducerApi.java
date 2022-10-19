package com.nestor.springcloudnacossentinelconsumer.producer;

import com.nestor.springcloudnacossentinelconsumer.common.SentinelProducerFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.nestor.springcloudnacossentinelconsumer.common.SentinelProducerFallBackFactory;
import com.nestor.springcloudnacossentinelconsumer.vo.Info;
import com.nestor.springcloudnacossentinelconsumer.vo.Result;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "${producer.service.name:producer}", path = "/producer", /*fallback = SentinelProducerFallBack.class, */fallbackFactory = SentinelProducerFallBackFactory.class)
// @RequestMapping("/producer")
public interface ProducerApi {

    @GetMapping(path = "/info")
    String info();

    @PostMapping(path = "/info")
    Result<String> info(@RequestBody Info info) throws Throwable;

}
