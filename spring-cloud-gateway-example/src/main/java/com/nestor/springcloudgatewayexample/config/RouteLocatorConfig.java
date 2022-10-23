package com.nestor.springcloudgatewayexample.config;

import java.net.URI;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.CollectionUtils;

import reactor.core.publisher.Mono;

@Configuration
public class RouteLocatorConfig {

    @Autowired
    private RouteLocatorBuilder builder;

    // @Bean
    // public RemoteAddressResolver xffRemoteAddressResolver() {
    // return XForwardedRemoteAddressResolver.maxTrustedIndex(4);
    // }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes().route("x-forwarded-for-route",
                predicateSpec -> predicateSpec.remoteAddr("127.0.0.1", "192.168.0.1", "0:0:0:0:0:0:0:1").and().path("/spring-cloud").uri(
                        URI.create("http://www.ityouknow.com"))).build();
    }

    @Bean
    public RouteLocator retryRouteLocator(@Qualifier("tokenGatewayFilter") GatewayFilter tokenGatewayFilter) {
        return builder.routes().route("retryRoute",
                predicateSpec -> predicateSpec.path("/producer/error").filters(
                        gatewayFilterSpec -> gatewayFilterSpec.filter(tokenGatewayFilter).retry(3)).uri(
                                "lb://nacos-producer")).build();
    }

    @Bean
    @Order(-1)
    public GlobalFilter customGlobalFilter() {
        return (exchange, chain) -> {
            System.err.println(String.format("[%s] customGlobalFilter pre....", System.currentTimeMillis()));
            return chain.filter(exchange).then(Mono.fromRunnable(() -> System.err.println(
                    String.format("[%s] customGlobalFilter post....", System.currentTimeMillis()))));
        };
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public GatewayFilter tokenGatewayFilter() {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            HttpHeaders headers = request.getHeaders();
            List<String> authorizations = headers.get("Authorization");
            System.err.println("Authorization: " + authorizations);
            if (CollectionUtils.isEmpty(authorizations)) {
                return exchange.getResponse().setComplete();
            }
            return chain.filter(exchange);
        };
    }

    public static void main(String[] args) {
        ZonedDateTime now = ZonedDateTime.now(ZoneId.systemDefault());
        System.out.println(now);
    }

}
