package com.nestor.springcloudnacossentinelproducer.common;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;

import lombok.extern.slf4j.Slf4j;

/**
 * 用于授权规则
 * @author : Nestor.Bian
 * @version : V 1.0
 * @date : 2021/5/16
 */
@Component
@Slf4j
public class SentinelRequestOriginParser implements RequestOriginParser {
    /**
     * Parse the origin from given HTTP request.
     *
     * @param request HTTP request
     * @return parsed origin
     */
    @Override
    public String parseOrigin(HttpServletRequest request) {

        String serverName = request.getParameter("serverName");
        log.info("用于授权规则的服务名:[{}]", serverName);

        return serverName;
    }
}
