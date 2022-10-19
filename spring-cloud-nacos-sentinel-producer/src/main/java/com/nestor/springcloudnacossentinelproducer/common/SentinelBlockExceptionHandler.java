package com.nestor.springcloudnacossentinelproducer.common;

import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;

import lombok.extern.slf4j.Slf4j;

/**
 * web sentinel通用处理器
 *
 * @author : Nestor.Bian
 * @version : V 1.0
 * @date : 2021/5/15
 */
@Component
@Slf4j
public class SentinelBlockExceptionHandler implements BlockExceptionHandler {


    /**
     * Handle the request when blocked.
     *
     * @param request  Servlet request
     * @param response Servlet response
     * @param e        the block exception
     * @throws Exception users may throw out the BlockException or other error occurs
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, BlockException e) throws Exception {
        String result = "";
        if (e instanceof FlowException) {
            result = "被限流了";
        } else if (e instanceof DegradeException) {
            result = "被降级了";
        } else if (e instanceof AuthorityException) {
            result = "被授权限制了";
        } else if (e instanceof ParamFlowException) {
            result = "被热点参数限制了";
        } else if (e instanceof SystemBlockException) {
            result = "被系统限制了";
        }

        log.error("SentinelBlockExceptionHandler捕获sentinel异常", e);
        response.setStatus(200);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getOutputStream().write(result.getBytes(StandardCharsets.UTF_8));
    }
}
