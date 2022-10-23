package com.nestor.springcloudgatewayexample.predicate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.cloud.gateway.handler.predicate.GatewayPredicate;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

/**
 * 检查是否认证
 *
 * @author : Nestor.Bian
 * @version : V 1.0
 * @date : 2022/10/23
 */
@Component
@Slf4j
public class CheckAuthRoutePredicateFactory
        extends AbstractRoutePredicateFactory<CheckAuthRoutePredicateFactory.Config> {

    public CheckAuthRoutePredicateFactory() {
        super(Config.class);
    }

    @Override
    public Predicate<ServerWebExchange> apply(Config config) {
        return (GatewayPredicate) serverWebExchange -> {
            List<String> values = serverWebExchange.getRequest().getHeaders().getOrDefault(config.name,
                    Collections.emptyList());
            if (values.isEmpty()) {
                log.info("CheckAuth {}未匹配上", config);
                return false;
            }

            if (values.stream().anyMatch(value -> Objects.equals(value, config.value))) {
                log.info("CheckAuth {}匹配上", config);
                return true;
            }

            log.info("CheckAuth {}未匹配上", config);
            return false;
        };
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("name", "value");
    }

    public static class Config {
        private String name;

        private String value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Config{" + "name='" + name + '\'' + ", value='" + value + '\'' + '}';
        }
    }

}
