package com.nestor.springcloudnacoshystrixconsumer.common;

import com.nestor.springcloudnacoshystrixconsumer.producer.ProducerApi;
import com.nestor.springcloudnacoshystrixconsumer.vo.Info;
import com.nestor.springcloudnacoshystrixconsumer.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * hystrix feign的降级类
 *
 * @author : Nestor.Bian
 * @version : V 1.0
 * @date : 2021/7/14
 */
@Slf4j
@Component
public class HystrixProducerFallBack implements ProducerApi {
    @Override
    public String info() {
        log.info("hystrix feign 降级方法触发了");
        return "降级了";
    }

    @Override
    public Result<String> info(Info info) throws Throwable {
        log.info("hystrix feign 降级方法触发了");
        return new Result<>("9999", "降级了");
    }
}
