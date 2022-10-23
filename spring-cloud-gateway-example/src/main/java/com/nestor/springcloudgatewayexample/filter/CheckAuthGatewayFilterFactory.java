package com.nestor.springcloudgatewayexample.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractNameValueGatewayFilterFactory;
import org.springframework.stereotype.Component;

/**
 * 自定义过滤器工厂
 *
 * @author : Nestor.Bian
 * @version : V 1.0
 * @date : 2022/10/23
 */
@Component
@Slf4j
public class CheckAuthGatewayFilterFactory extends AbstractNameValueGatewayFilterFactory {
    @Override
    public GatewayFilter apply(NameValueConfig config) {
        return (exchange, chain) -> {
            log.info("CheckAuthGatewayFilterFactory {}", config);
            return chain.filter(exchange);
        };
    }
}
