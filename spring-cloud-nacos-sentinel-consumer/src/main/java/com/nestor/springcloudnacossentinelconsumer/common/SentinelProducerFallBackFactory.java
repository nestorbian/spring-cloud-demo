package com.nestor.springcloudnacossentinelconsumer.common;

import org.springframework.stereotype.Component;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.nestor.springcloudnacossentinelconsumer.producer.ProducerApi;
import com.nestor.springcloudnacossentinelconsumer.vo.Info;
import com.nestor.springcloudnacossentinelconsumer.vo.Result;

import feign.hystrix.FallbackFactory;

/**
 * sentinel producer服务的回调工厂
 *
 * @author : Nestor.Bian
 * @version : V 1.0
 * @date : 2021/5/26
 */
@Component
public class SentinelProducerFallBackFactory implements FallbackFactory<ProducerApi> {

	@Override
	public ProducerApi create(Throwable throwable) {
		return new ProducerApi() {
			@Override
			public String info() {
				if (throwable instanceof BlockException) {
					String result = "";
					if (throwable instanceof FlowException) {
						result = "被限流了";
					} else if (throwable instanceof DegradeException) {
						result = "被降级了";
					} else if (throwable instanceof AuthorityException) {
						result = "被授权限制了";
					} else if (throwable instanceof ParamFlowException) {
						result = "被热点参数限制了";
					} else if (throwable instanceof SystemBlockException) {
						result = "被系统限制了";
					}
					return result;
				}
				return "服务被业务异常降级了";
			}

			@Override
			public Result<String> info(Info info) throws Throwable {
				if (throwable instanceof BlockException) {
					String result = "";
					if (throwable instanceof FlowException) {
						result = "被限流了";
					} else if (throwable instanceof DegradeException) {
						result = "被降级了";
					} else if (throwable instanceof AuthorityException) {
						result = "被授权限制了";
					} else if (throwable instanceof ParamFlowException) {
						result = "被热点参数限制了";
					} else if (throwable instanceof SystemBlockException) {
						result = "被系统限制了";
					}
					return new Result<>("200", result);
				}

				// return new Result<>("200", "服务被业务异常降级了");
                throw throwable;
			}
		};
	}
}
