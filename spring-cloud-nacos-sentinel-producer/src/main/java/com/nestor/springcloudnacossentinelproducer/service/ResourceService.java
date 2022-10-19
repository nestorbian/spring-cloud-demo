package com.nestor.springcloudnacossentinelproducer.service;

import org.springframework.stereotype.Service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.nestor.springcloudnacossentinelproducer.common.SentinelExceptionHandler;

/**
 * 资源服务
 *
 * @author : Nestor.Bian
 * @version : V 1.0
 * @date : 2021/5/16
 */
@Service
public class ResourceService {

	@SentinelResource(value = "getResource", blockHandler = "blockHandle", blockHandlerClass = SentinelExceptionHandler.class)
	public String getResource() {
		return "RESOURCE";
	}

}
