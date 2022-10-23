package com.nestor.springcloudnacossentinelconsumer.common;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;

import com.alibaba.cloud.sentinel.rest.SentinelClientHttpResponse;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nestor.springcloudnacossentinelconsumer.vo.Result;

import lombok.extern.slf4j.Slf4j;

/**
 * sentinel通用异常处理
 *
 * @author : Nestor.Bian
 * @version : V 1.0
 * @date : 2021/5/15
 */
@Slf4j
public class SentinelExceptionHandler {

	public static ClientHttpResponse blockHandle(HttpRequest request, byte[] body, ClientHttpRequestExecution execution,
			BlockException blockException) throws JsonProcessingException {
		log.error("SentienlExceptionHandler捕获sentinel异常", blockException);
		String result = "";
		if (blockException instanceof FlowException) {
			result = "被限流了";
		} else if (blockException instanceof DegradeException) {
			result = "被降级了";
		} else if (blockException instanceof AuthorityException) {
			result = "被授权限制了";
		} else if (blockException instanceof ParamFlowException) {
			result = "被热点参数限制了";
		} else if (blockException instanceof SystemBlockException) {
			result = "被系统限制了";
		}

		return new SentinelClientHttpResponse(new ObjectMapper().writeValueAsString(new Result<>("200", result)));
	}

	public static ClientHttpResponse fallback(HttpRequest request, byte[] body, ClientHttpRequestExecution execution,
			Throwable throwable) throws JsonProcessingException {
		log.error("SentienlExceptionHandler捕获业务异常", throwable);
		return new SentinelClientHttpResponse(new ObjectMapper().writeValueAsString(new Result<>("200", "发生业务异常了")));
	}

}
