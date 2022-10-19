package com.nestor.springcloudnacoshystrixproducer.command;

import com.nestor.springcloudopenfeignapi.vo.Result;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import lombok.extern.slf4j.Slf4j;

/**
 * 自定义hystrix命令
 *
 * @author : Nestor.Bian
 * @version : V 1.0
 * @date : 2021/7/11
 */
@Slf4j
public class MyHystrixCommand extends HystrixCommand<Result<String>> {
    public MyHystrixCommand(String groupKey) {
        super(HystrixCommandGroupKey.Factory.asKey(groupKey));
    }

    @Override
    protected Result<String> run() throws Exception {

        // if (Math.random() < 0.5) {
        //     throw new RuntimeException("触发随机异常");
        // }
        Thread.sleep(10000);

        return new Result<>("0000", "SUCCESS");
    }

    @Override
    protected Result<String> getFallback() {
        log.warn("MyHystrixCommand降级方法触发");
        return new Result<>("9999", "降级方法触发");
    }
}
