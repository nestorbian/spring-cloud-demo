package com.nestor.springcloudnacossentinelconsumer.support;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * @author : Nestor.Bian
 * @version : V 1.0
 * @date : 2020/10/21
 */
public class TokenRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String authorization = request.getHeader("Authorization");
        requestTemplate.header("Token", authorization);
    }
}
