package com.nestor.springcloudnacoshystrixproducer.service;

import com.nestor.springcloudopenfeignapi.vo.Result;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * hystrix服务
 *
 * @author : Nestor.Bian
 * @version : V 1.0
 * @date : 2021/7/13
 */
@Service
@Slf4j
public class HystrixService {

    /**
     * 测试针对service方法熔断降级
     *
     * @param
     * @return com.nestor.springcloudopenfeignapi.vo.Result<java.lang.String>
     * @date : 2021/7/13 14:58
     * @author : Nestor.Bian
     * @since : 1.0
     */
    @HystrixCommand(fallbackMethod = "fallback")
    public Result<String> hystrixBrokerMethod() throws Exception {
        Thread.sleep(2000);
        return new Result<>("0000", "SUCCESS");
    }

    private Result<String> fallback() {
        log.warn("fallback降级方法触发");
        return new Result<>("9999", "降级方法触发");
    }

}
