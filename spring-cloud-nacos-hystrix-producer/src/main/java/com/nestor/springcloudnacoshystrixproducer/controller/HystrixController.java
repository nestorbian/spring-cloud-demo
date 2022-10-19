package com.nestor.springcloudnacoshystrixproducer.controller;

import com.nestor.springcloudnacoshystrixproducer.command.MyHystrixCommand;
import com.nestor.springcloudnacoshystrixproducer.service.HystrixService;
import com.nestor.springcloudopenfeignapi.producer.ProducerApi;
import com.nestor.springcloudopenfeignapi.vo.Info;
import com.nestor.springcloudopenfeignapi.vo.Result;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * hystrix控制器
 *
 * @author : Nestor.Bian
 * @version : V 1.0
 * @date : 2021/7/11
 */
@RestController
@Slf4j
@RequestMapping("/producer")
public class HystrixController implements ProducerApi {

    @Autowired
    private HystrixService hystrixService;

    @GetMapping("/hystrix-command")
    public Result<String> hystrixCommand() {
        return new MyHystrixCommand("MyHystrixGroupKey").execute();
    }

    /**
     * 指定降级方法
     *
     * @param
     * @return com.nestor.springcloudopenfeignapi.vo.Result<java.lang.String>
     * @date : 2021/7/11 21:36
     * @author : Nestor.Bian
     * @since : 1.0
     */
    @HystrixCommand(groupKey = "MyHystrixGroupKey", fallbackMethod = "hystrixAnnotation1Fallback")
    @GetMapping("/hystrix-annotation1")
    public Result<String> hystrixAnnotation1() {
        if (Math.random() < 0.5) {
            throw new RuntimeException("触发随机异常");
        }
        return new Result<>("0000", "SUCCESS");
    }

    /**
     * hystrixAnnotation1降级方法
     *
     * @param
     * @return com.nestor.springcloudopenfeignapi.vo.Result<java.lang.String>
     * @date : 2021/7/11 21:33
     * @author : Nestor.Bian
     * @since : 1.0
     */
    private Result<String> hystrixAnnotation1Fallback(Throwable ex) {
        log.warn("hystrixAnnotation1降级方法触发", ex);
        return new Result<>("9999", "降级方法触发");
    }

    /**
     * 不指定降级方法
     *
     * @param
     * @return com.nestor.springcloudopenfeignapi.vo.Result<java.lang.String>
     * @date : 2021/7/11 21:36
     * @author : Nestor.Bian
     * @since : 1.0
     */
    @HystrixCommand(groupKey = "MyHystrixGroupKey")
    @GetMapping("/hystrix-annotation2")
    public Result<String> hystrixAnnotation2() {
        if (Math.random() < 0.5) {
            throw new RuntimeException("触发随机异常");
        }
        return new Result<>("0000", "SUCCESS");
    }

    /**
     * 超时熔断降级1
     *
     * @param
     * @return com.nestor.springcloudopenfeignapi.vo.Result<java.lang.String>
     * @date : 2021/7/12 21:46
     * @author : Nestor.Bian
     * @since : 1.0
     */
    @HystrixCommand(groupKey = "MyHystrixGroupKey", fallbackMethod = "hystrixAnnotation1Fallback", commandKey = "hystrix-annotation3")
    @GetMapping("/hystrix-annotation3")
    public Result<String> hystrixAnnotation3() throws InterruptedException {
        Thread.sleep(2000);

        return new Result<>("0000", "SUCCESS");
    }

    /**
     * 超时熔断降级2
     *
     * @param
     * @return com.nestor.springcloudopenfeignapi.vo.Result<java.lang.String>
     * @date : 2021/7/12 21:46
     * @author : Nestor.Bian
     * @since : 1.0
     */
    @HystrixCommand(groupKey = "MyHystrixGroupKey", fallbackMethod = "hystrixAnnotation1Fallback")
    @GetMapping("/hystrix-annotation4")
    public Result<String> hystrixAnnotation4() throws InterruptedException {
        Thread.sleep(2000);

        return new Result<>("0000", "SUCCESS");
    }

    /**
     * 异常熔断降级
     *
     * @param id
     * @return com.nestor.springcloudopenfeignapi.vo.Result<java.lang.String>
     * @date : 2021/7/13 12:22
     * @author : Nestor.Bian
     * @since : 1.0
     */
    @HystrixCommand(groupKey = "MyHystrixGroupKey", fallbackMethod = "hystrixAnnotation5Fallback", commandKey = "hystrix-annotation5")
    @GetMapping("/hystrix-annotation5")
    public Result<String> hystrixAnnotation5(@RequestParam(required = false) Integer id) throws InterruptedException {
        if (id == null) {
            throw new RuntimeException("id为null");
        }

        return new Result<>("0000", "SUCCESS");
    }

    private Result<String> hystrixAnnotation5Fallback(Integer id) {
        log.warn("hystrixAnnotation1降级方法触发");
        return new Result<>("9999", "降级方法触发");
    }

    /**
     * 测试hystrix针对service方法熔断限流
     *
     * @param
     * @return com.nestor.springcloudopenfeignapi.vo.Result<java.lang.String>
     * @date : 2021/7/13 15:01
     * @author : Nestor.Bian
     * @since : 1.0
     */
    @GetMapping("/hystrix-service")
    public Result<String> hystrixServiceMethod() throws Exception {
        return hystrixService.hystrixBrokerMethod();
    }

    /**
     * @HystrixProperty 配置信号量隔离
     *
     * @param id
     * @return com.nestor.springcloudopenfeignapi.vo.Result<java.lang.String>
     * @date : 2021/7/15 19:02
     * @author : Nestor.Bian
     * @since : 1.0
     */
    @HystrixCommand(groupKey = "MyHystrixGroupKey", fallbackMethod = "hystrixAnnotation6Fallback", commandKey = "hystrix-annotation6", commandProperties = {
            @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE"),
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
            @HystrixProperty(name = "execution.isolation.semaphore.maxConcurrentRequests", value = "5") })
    @GetMapping("/hystrix-annotation6")
    public Result<String> hystrixAnnotation6(@RequestParam(required = false) Integer id) throws InterruptedException {
        // if (id == null) {
        // 	throw new RuntimeException("id为null");
        // }
        Thread.sleep(2500);

        return new Result<>("0000", "SUCCESS");
    }

    private Result<String> hystrixAnnotation6Fallback(Integer id, Throwable ex) {
        log.warn("hystrixAnnotation6降级方法触发", ex);
        return new Result<>("9999", "降级方法触发");
    }

    /**
     * hystrix线程隔离 限流测试
     *
     * @param
     * @return com.nestor.springcloudopenfeignapi.vo.Result<java.lang.String>
     * @date : 2021/7/16 11:23
     * @author : Nestor.Bian
     * @since : 1.0
     */
    @HystrixCommand(groupKey = "MyHystrixGroupKey", fallbackMethod = "hystrixAnnotation1Fallback", threadPoolKey = "hystrix-annotation7")
    @GetMapping("/hystrix-annotation7")
    public Result<String> hystrixAnnotation7() throws InterruptedException {
        Thread.sleep(500);

        return new Result<>("0000", "SUCCESS");
    }

    /********************************** feign **********************************/

    @Value("${server.port}")
    private int port;

    private int i = 0;

    @Override
    public String info() {
        log.info("port:[{}]的服务提供者的info方法开始被调用", port);
        if (i < 2) {
            i++;
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        log.info("port:[{}]的服务提供者的info方法结束被调用", port);

        return "SUCCESS";
    }

    @Override
    public Result<String> info(Info info) {
        log.info("port:[{}]的服务提供者被调用, 传入参数:[{}]", port, info);
        // log.info("TOKEN:[{}]", request.getHeader("Token"));
        // LOGGER.info("port:[{}]的服务提供者的post info方法开始被调用", port);
        // try {
        // Thread.sleep(3000);
        // } catch (InterruptedException e) {
        // e.printStackTrace();
        // }
        // LOGGER.info("port:[{}]的服务提供者的post info方法结束被调用", port);
        if (Math.random() > 0.5) {
            throw new RuntimeException("xxxxxx");
        }
        return new Result<>("200", "SUCCESS");
    }

    /********************************** feign **********************************/

}
