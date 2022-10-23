package com.nestor.springcloudgatewaysentinelexample.config;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.ServerWebExchange;

import com.alibaba.csp.sentinel.adapter.gateway.common.SentinelGatewayConstants;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiDefinition;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPathPredicateItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPredicateItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.GatewayApiDefinitionManager;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.SentinelGatewayFilter;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.exception.SentinelGatewayBlockExceptionHandler;

import reactor.core.publisher.Mono;

/**
 * 网关限流配置
 *
 * @author : Nestor.Bian
 * @version : V 1.0
 * @date : 2022/10/23
 */
@Configuration
public class GatewaySentinelConfig {

    private final List<ViewResolver> viewResolvers;

    private final ServerCodecConfigurer serverCodecConfigurer;

    public GatewaySentinelConfig(List<ViewResolver> viewResolvers, ServerCodecConfigurer serverCodecConfigurer) {
        this.viewResolvers = viewResolvers;
        this.serverCodecConfigurer = serverCodecConfigurer;
    }

    /**
     * 限流异常处理器
     * 
     * @return
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SentinelGatewayBlockExceptionHandler sentinelGatewayBlockExceptionHandler() {
        return new SentinelGatewayBlockExceptionHandler(viewResolvers, serverCodecConfigurer);
    }

    /**
     * 限流过滤器
     * 
     * @return
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SentinelGatewayFilter sentinelGatewayFilter() {
        return new SentinelGatewayFilter();
    }

    @PostConstruct
    public void init() {
        //初始化自定义的API
        initCustomizedApis();
        //初始化网关限流规则
        initGatewayRules();
        //自定义限流异常处理器
        initBlockRequestHandler();
    }

    private void initCustomizedApis() {
        Set<ApiDefinition> definitions = new HashSet<>();
        // 资源保护名
        ApiDefinition api = new ApiDefinition("testgateway_api").setPredicateItems(new HashSet<ApiPredicateItem>() {
            {
                add(new ApiPathPredicateItem().setPattern("/producer/test*").setMatchStrategy(
                        SentinelGatewayConstants.URL_MATCH_STRATEGY_PREFIX));
                // 精确匹配 默认SentinelGatewayConstants.URL_MATCH_STRATEGY_EXACT
                // add(new ApiPathPredicateItem().setPattern("/producer/testgateway").setMatchStrategy(
                //         SentinelGatewayConstants.URL_MATCH_STRATEGY_EXACT));
            }
        });
        definitions.add(api);
        GatewayApiDefinitionManager.loadApiDefinitions(definitions);
    }

    private void initGatewayRules() {
        Set<GatewayFlowRule> rules = new HashSet<>();
        // resource：资源名称，可以是网关中的 route 名称或者用户自定义的 API 分组名称。
        // count：限流阈值
        // intervalSec：统计时间窗口，单位是秒，默认是 1 秒。
        rules.add(new GatewayFlowRule("info_route").setCount(1).setIntervalSec(1));
        rules.add(new GatewayFlowRule("testgateway_api").setCount(2).setIntervalSec(1));

        // 加载网关规则
        GatewayRuleManager.loadRules(rules);
    }

    private void initBlockRequestHandler() {
        BlockRequestHandler blockRequestHandler = new BlockRequestHandler() {
            @Override
            public Mono<ServerResponse> handleRequest(ServerWebExchange exchange, Throwable t) {
                HashMap<String, String> result = new HashMap<>();
                result.put("code", String.valueOf(HttpStatus.TOO_MANY_REQUESTS.value()));
                result.put("msg", HttpStatus.TOO_MANY_REQUESTS.getReasonPhrase());

                return ServerResponse.status(HttpStatus.TOO_MANY_REQUESTS).contentType(MediaType.APPLICATION_JSON).body(
                        BodyInserters.fromValue(result));
            }
        };
        // 设置自定义异常处理器 对应源码：SentinelGatewayBlockExceptionHandler.handleBlockedRequest()
        GatewayCallbackManager.setBlockHandler(blockRequestHandler);
    }

}
