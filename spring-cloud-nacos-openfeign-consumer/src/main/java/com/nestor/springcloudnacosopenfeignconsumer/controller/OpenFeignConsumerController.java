package com.nestor.springcloudnacosopenfeignconsumer.controller;

import com.nestor.springcloudopenfeignapi.vo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nestor.springcloudopenfeignapi.producer.ProducerApi;
import com.nestor.springcloudopenfeignapi.vo.Info;

/**
 * @author : Nestor.Bian
 * @version : V 1.0
 * @date : 2020/10/19
 */
@RestController
@RefreshScope
public class OpenFeignConsumerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OpenFeignConsumerController.class);

    @Autowired
    private ProducerApi producerApi;

    @Value("${isNewBusiness}")
    private Boolean isNewBusiness;

    @Value("${ext1.config.value:}")
    private String ext1ConfigValue;

    @Value("${ext2.config.value:}")
    private String ext2ConfigValue;

    @Value("${common1.config.value:}")
    private String common1ConfigValue;

    @Value("${common2.config.value:}")
    private String common2ConfigValue;

    @Autowired
    private Environment environment;

    @PostMapping(path = "/info")
    public Result<String> info(@RequestBody Info info) {
        LOGGER.info("======request body:[{}]=======", info);
        return producerApi.info(info);
    }

    @GetMapping(path = "/info")
    public String info() {
        LOGGER.info("isNewBusiness:[{}]", isNewBusiness);
        LOGGER.info("ext1ConfigValue:[{}]", ext1ConfigValue);
        LOGGER.info("ext2ConfigValue:[{}]", ext2ConfigValue);
        LOGGER.info("common1ConfigValue:[{}]", common1ConfigValue);
        LOGGER.info("common2ConfigValue:[{}]", common2ConfigValue);
        return "SUCCESS";
    }

    @GetMapping(path = "/bg-info")
    public String bgInfo() {
        new Thread(() -> {
            while (true) {
                LOGGER.info("isNewBusiness=[{}]", environment.getProperty("isNewBusiness", Boolean.class));
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();
        return "SUCCESS";
    }

}
