package com.nestor.springcloudopenfeignapi.producer;

import com.nestor.springcloudopenfeignapi.vo.Info;
import com.nestor.springcloudopenfeignapi.vo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "${producer.service.name:producer}", path = "/producer")
// @RequestMapping("/producer")
public interface ProducerApi {

    @GetMapping(path = "/info")
    String info();

    @PostMapping(path = "/info")
    Result<String> info(@RequestBody Info info);

}
