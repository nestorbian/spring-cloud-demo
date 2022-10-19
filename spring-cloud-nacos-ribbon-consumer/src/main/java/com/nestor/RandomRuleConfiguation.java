package com.nestor;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : Nestor.Bian
 * @version : V 1.0
 * @date : 2020/9/30
 */
@Configuration
public class RandomRuleConfiguation {

    @Bean
    public IRule randomRule() {
        return new RandomRule();
    }

}
