package com.nestor.springcloudnacossentinelconsumer.common;

import org.springframework.stereotype.Component;

import com.nestor.springcloudnacossentinelconsumer.producer.ProducerApi;
import com.nestor.springcloudnacossentinelconsumer.vo.Info;
import com.nestor.springcloudnacossentinelconsumer.vo.Result;

/**
 * producer服务的sentinel回调
 *
 * @author : Nestor.Bian
 * @version : V 1.0
 * @date : 2021/5/26
 */
@Component
public class SentinelProducerFallBack implements ProducerApi {
    @Override
    public String info() {
        return "sentinel限流了";
    }

    @Override
    public Result<String> info(Info info) {
        return new Result<>("000", "sentinel限流了");
    }

}
