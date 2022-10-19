package com.nestor.springcloudnacossentinelproducer.common;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;

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

    public static String blockHandle(BlockException blockException) {
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

        return result;
    }

    /**
     * 必须与限流方法的参数一致，否则无法获取限流处理的方法
     *
     * @param id
     * @param blockException
     * @return java.lang.String
     * @date : 2021/5/16 17:06
     * @author : Nestor.Bian
     * @since : 1.0
     */
    public static String blockHandle(Integer id, BlockException blockException) {
        log.error("SentienlExceptionHandler捕获sentinel异常,id[{}]", id, blockException);
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

        return result;
    }

    public static String fallback(Throwable throwable) {
        log.error("SentienlExceptionHandler捕获业务异常", throwable);
        return "发生业务异常了";
    }

}
