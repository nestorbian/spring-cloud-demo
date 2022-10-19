package com.nestor.springcloudnacossentinelconsumer.config;

import com.alibaba.cloud.sentinel.annotation.SentinelRestTemplate;
import com.nestor.springcloudnacossentinelconsumer.common.SentinelExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

/**
 * @author : Nestor.Bian
 * @version : V 1.0
 * @date : 2021/5/24
 */
@Configuration
@Slf4j
public class RibbonConfig {

	@Bean
	@LoadBalanced
	@SentinelRestTemplate(blockHandler = "blockHandle", blockHandlerClass = SentinelExceptionHandler.class, fallback = "fallback", fallbackClass = SentinelExceptionHandler.class)
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.interceptors(new CustomRequestInterceptor()).build();
	}

	private class CustomRequestInterceptor implements ClientHttpRequestInterceptor {

		@Override
		public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes,
				ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
			log.info("uri:[{}]", httpRequest.getURI());
			return clientHttpRequestExecution.execute(httpRequest, bytes);
		}
	}
}
