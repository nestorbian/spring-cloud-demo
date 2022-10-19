package com.nestor.springcloudnacossentinelproducer.controller;

import java.util.Collections;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.nestor.springcloudnacossentinelproducer.common.SentinelExceptionHandler;
import com.nestor.springcloudnacossentinelproducer.service.ResourceService;
import com.nestor.springcloudopenfeignapi.producer.ProducerApi;
import com.nestor.springcloudopenfeignapi.vo.Info;
import com.nestor.springcloudopenfeignapi.vo.Result;

import lombok.extern.slf4j.Slf4j;

/**
 * controller
 *
 * @author : Nestor.Bian
 * @version : V 1.0
 * @date : 2021/5/11
 */
@RestController
@RequestMapping(path = "/producer")
@Slf4j
public class ProducerController implements InitializingBean, ProducerApi {

	@Autowired
	private ResourceService resourceService;

	/**
	 * sentinel-core使用方法
	 *
	 * @param
	 * @return java.lang.String
	 * @date : 2021/5/15 17:16
	 * @author : Nestor.Bian
	 * @since : 1.0
	 */
	@GetMapping("/hello-sentinel")
	public String helloSentinel() {

		Entry entry = null;

		try {
			entry = SphU.entry("hello-sentinel");
			// throw new RuntimeException("xxxxxx");
			System.err.println("hello sentinel ...");
		} catch (BlockException blockException) {
			return "触发了限流或者熔断";
		} catch (Exception e) {
			Tracer.traceEntry(e, entry);
			return "发生业务异常";
		} finally {
			if (entry != null) {
				entry.exit();
			}
		}

		return "SUCCESS";
	}

	/**
	 * 测试限流
	 *
	 * @param
	 * @return java.lang.String
	 * @date : 2021/5/15 18:02
	 * @author : Nestor.Bian
	 * @since : 1.0
	 */
	@GetMapping("/flow")
	@SentinelResource(value = "flow", blockHandler = "blockHandle", blockHandlerClass = SentinelExceptionHandler.class, fallback = "fallback", fallbackClass = SentinelExceptionHandler.class)
	public String flow() {
		// 验证线程数
		// try {
		// System.err.println("processing...");
		// Thread.sleep(5000);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		return "SUCCESS";
	}

	@GetMapping("/link-flow")
	@SentinelResource(value = "link-flow", blockHandler = "blockHandle", blockHandlerClass = SentinelExceptionHandler.class, fallback = "fallback", fallbackClass = SentinelExceptionHandler.class)
	public String test2() {
		log.info("processing...");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "SUCCESS";
	}

	@GetMapping("/chain-flow1")
	@SentinelResource(value = "chain-flow1", blockHandler = "blockHandle", blockHandlerClass = SentinelExceptionHandler.class, fallback = "fallback", fallbackClass = SentinelExceptionHandler.class)
	public String test3() {
		log.info("chain-flow1 processing...");
		try {
			Thread.sleep(500);
			String resource = resourceService.getResource();
			log.info("chain-flow1 " + resource);
			return resource;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "SUCCESS";
	}

	@GetMapping("/chain-flow2")
	@SentinelResource(value = "chain-flow2", blockHandler = "blockHandle", blockHandlerClass = SentinelExceptionHandler.class, fallback = "fallback", fallbackClass = SentinelExceptionHandler.class)
	public String test4() {
		log.info("chain-flow2 processing...");
		try {
			Thread.sleep(500);
			String resource = resourceService.getResource();
			log.info("chain-flow2 " + resource);
			return resource;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "SUCCESS";
	}

	@GetMapping("/warm-up-flow")
	@SentinelResource(value = "warm-up-flow", blockHandler = "blockHandle", blockHandlerClass = SentinelExceptionHandler.class, fallback = "fallback", fallbackClass = SentinelExceptionHandler.class)
	public String test5() {
		log.info("warm-up-flow processing...");
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "SUCCESS";
	}

	@GetMapping("/queue-flow")
	@SentinelResource(value = "queue-flow", blockHandler = "blockHandle", blockHandlerClass = SentinelExceptionHandler.class, fallback = "fallback", fallbackClass = SentinelExceptionHandler.class)
	public String test6() {
		log.info("queue-flow processing...");
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "SUCCESS";
	}

	@GetMapping("/rt-breaker")
	@SentinelResource(value = "rt-breaker", blockHandler = "blockHandle", blockHandlerClass = SentinelExceptionHandler.class, fallback = "fallback", fallbackClass = SentinelExceptionHandler.class)
	public String test7() {
		log.info("rt-breaker processing...");
		try {
			Thread.sleep((long) (150 * Math.random()));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "SUCCESS";
	}

	@GetMapping("/exception-rate-breaker")
	@SentinelResource(value = "exception-rate-breaker", blockHandler = "blockHandle", blockHandlerClass = SentinelExceptionHandler.class, fallback = "fallback", fallbackClass = SentinelExceptionHandler.class)
	public String test8() {
		log.info("exception-rate-breaker processing...");

		if (Math.random() < 0.5) {
			throw new RuntimeException("降级异常");
		}

		return "SUCCESS";
	}

	@GetMapping("/hot-param/{id}")
	@SentinelResource(value = "hot-param", blockHandler = "blockHandle", blockHandlerClass = SentinelExceptionHandler.class, fallback = "fallback", fallbackClass = SentinelExceptionHandler.class)
	public String test9(@PathVariable("id") Integer id) {
		log.info("hot-param {} processing...", id);

		return "SUCCESS";
	}

	@GetMapping("/auth")
	@SentinelResource(value = "auth", blockHandler = "blockHandle", blockHandlerClass = SentinelExceptionHandler.class, fallback = "fallback", fallbackClass = SentinelExceptionHandler.class)
	public String test9() {
		log.info("auth processing...");

		return "SUCCESS";
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// 客户端指定限流规则
		FlowRule flowRule = new FlowRule();
		flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
		flowRule.setClusterMode(false);
		flowRule.setResource("hello-sentinel");
		flowRule.setStrategy(RuleConstant.STRATEGY_DIRECT);
		flowRule.setCount(1);
		FlowRuleManager.loadRules(Collections.singletonList(flowRule));
	}

	@Value("${server.port}")
	private int port;

	@Override
	public String info() {
		log.info("port:[{}]的服务提供者的info方法开始被调用", port);
		// try {
		// 	Thread.sleep(3000);
		// } catch (InterruptedException e) {
		// 	e.printStackTrace();
		// }
		log.info("port:[{}]的服务提供者的info方法结束被调用", port);

		return "SUCCESS";
	}

	@Override
	public Result<String> info(Info info) {
		log.info("port:[{}]的服务提供者被调用, 传入参数:[{}]", port, info);
		// log.info("TOKEN:[{}]", request.getHeader("Token"));
		// LOGGER.info("port:[{}]的服务提供者的post info方法开始被调用", port);
		// try {
		// 	Thread.sleep(3000);
		// } catch (InterruptedException e) {
		// 	e.printStackTrace();
		// }
		// LOGGER.info("port:[{}]的服务提供者的post info方法结束被调用", port);
		if (Math.random() > 0.5) {
			throw new RuntimeException("xxxxxx");
		}
		return new Result<>("200", "SUCCESS");
	}
}
