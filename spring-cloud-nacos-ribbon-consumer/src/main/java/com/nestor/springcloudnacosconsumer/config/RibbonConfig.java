package com.nestor.springcloudnacosconsumer.config;

import com.nestor.RandomRuleConfiguation;
import com.netflix.loadbalancer.RoundRobinRule;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Ribbon 配置
 *
 * @author : Nestor.Bian
 * @version : V 1.0
 * @date : 2020/9/28
 */
@Configuration
// @RibbonClients({ @RibbonClient(name = "nacos-producer", configuration = RoundRobinRule.class),
// @RibbonClient(name = "nacos-producer1", configuration = RandomRuleConfiguation.class) })
public class RibbonConfig {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
