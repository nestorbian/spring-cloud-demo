package com.nestor.springcloudnacosproducer.controller;

import com.nestor.springcloudnacosproducer.vo.OrderVO;
import com.nestor.springcloudnacosproducer.vo.ProductVO;
import com.nestor.springcloudnacosproducer.vo.SimpleVo;
import com.nestor.springcloudopenfeignapi.producer.ProducerApi;
import com.nestor.springcloudopenfeignapi.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nestor.springcloudopenfeignapi.vo.Info;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.beans.PropertyEditorSupport;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : Nestor.Bian
 * @version : V 1.0
 * @date : 2020/9/21
 */
@RestController
@RequestMapping("/producer")
@Slf4j
public class ProducerController implements ProducerApi {

    @Autowired
    private HttpServletRequest request;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProducerController.class);

    @Value("${server.port}")
    private int port;

    // feign的继承性
    @GetMapping(path = "/info")
    public String info() {
        LOGGER.info("port:[{}]的服务提供者的info方法开始被调用", port);
        // try {
        // 	Thread.sleep(3000);
        // } catch (InterruptedException e) {
        // 	e.printStackTrace();
        // }
        // if (true) {
        //     throw new RuntimeException("info接口发生错误");
        // }
        LOGGER.info("port:[{}]的服务提供者的info方法结束被调用", port);

        return "SUCCESS";
    }

    // feign的继承性
    @PostMapping(path = "/info")
    public Result<String> info(@RequestBody Info info) {
        LOGGER.info("port:[{}]的服务提供者被调用, 传入参数:[{}]", port, info);
        LOGGER.info("TOKEN:[{}]", request.getHeader("Token"));
        LOGGER.info("port:[{}]的服务提供者的post info方法开始被调用", port);
        try {
        	Thread.sleep(3000);
        } catch (InterruptedException e) {
        	e.printStackTrace();
        }
        LOGGER.info("port:[{}]的服务提供者的post info方法结束被调用", port);
        return new Result<>("200", "SUCCESS");
    }

    @GetMapping("/test2")
    public String test2() {
        log.info("processing...");

        // try (InputStream inputStream = Files.newInputStream(Paths.get("F:\\电子书\\Redis从入门到高可用，分布式实践-笔记-www.itmuch" +
        //         ".com.pdf"))) {
        //     while (inputStream.read() != -1) ;
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "SUCCESS";
    }

    @GetMapping("testIds")
    public String testList(LocalDateTime ids) {
        return "SUCCESS" + ids;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // @RequestParam后的参数 public String testList(@RequestParam LocalDateTime ids)
        binder.registerCustomEditor(LocalDateTime.class, new CustomPropertyEditor());
        // 用于实体里的属性 public String testList(SimpleVo simpleVo)
        // binder.registerCustomEditor(LocalDateTime.class, "ids", new CustomPropertyEditor());
    }

    class CustomPropertyEditor extends PropertyEditorSupport {
        @Override
        public String getAsText() {
            return super.getAsText();
        }


        @Override
        public void setAsText(String text) throws IllegalArgumentException {
            System.out.println("text:" + text);
            setValue(LocalDateTime.now());
        }
    }

    @GetMapping("/testgateway")
    public String testGateway(@RequestHeader(value = "X-Request-color", required = false) String color) throws Exception {
        log.info("gateWay获取请求头X-Request-color："+color);
        return "success";
    }

    @GetMapping("/testgateway2")
    public String testGateway2(@RequestParam(value = "color", required = false) String color) throws Exception {
        log.info("gateWay获取请求参数color:"+color);
        return "success";
    }

}
