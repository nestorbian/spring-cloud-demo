// package com.nestor.springcloudnacossentinelproducer.common;
//
// import java.io.IOException;
// import java.nio.charset.StandardCharsets;
//
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
//
// import org.springframework.boot.web.servlet.FilterRegistrationBean;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.http.MediaType;
//
// import com.alibaba.csp.sentinel.adapter.servlet.CommonFilter;
// import com.alibaba.csp.sentinel.adapter.servlet.callback.UrlBlockHandler;
// import com.alibaba.csp.sentinel.adapter.servlet.callback.WebCallbackManager;
// import com.alibaba.csp.sentinel.slots.block.BlockException;
// import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
// import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
// import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
// import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
// import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
//
// import lombok.extern.slf4j.Slf4j;
//
// /**
//  * 解决流控链路不生效的问题 CommonFilter
//  * @author : Nestor.Bian
//  * @version : V 1.0
//  * @date : 2021/5/16
//  */
// @Configuration
// public class SentinelConfig {
//     @Bean
//     public FilterRegistrationBean sentinelFilterRegistration() {
//         FilterRegistrationBean registration = new FilterRegistrationBean();
//         registration.setFilter(new CommonFilter());
//         registration.addUrlPatterns("/*");
//         // 入口资源关闭聚合   解决流控链路不生效的问题
//         registration.addInitParameter(CommonFilter.WEB_CONTEXT_UNIFY, "false");
//         registration.setName("sentinelFilter");
//         registration.setOrder(1);
//
//         //CommonFilter的BlockException自定义处理逻辑
//         WebCallbackManager.setUrlBlockHandler(new MyUrlBlockHandler());
//
//         return registration;
//     }
//
//     // UrlBlockHandler的实现类
//     @Slf4j
//     public static class MyUrlBlockHandler implements UrlBlockHandler {
//         @Override
//         public void blocked(HttpServletRequest request, HttpServletResponse response, BlockException e) throws IOException {
//             String result = "";
//             if (e instanceof FlowException) {
//                 result = "被限流了";
//             } else if (e instanceof DegradeException) {
//                 result = "被降级了";
//             } else if (e instanceof AuthorityException) {
//                 result = "被授权限制了";
//             } else if (e instanceof ParamFlowException) {
//                 result = "被热点参数限制了";
//             } else if (e instanceof SystemBlockException) {
//                 result = "被系统限制了";
//             }
//
//             log.error("MyUrlBlockHandler捕获sentinel异常", e);
//             response.setStatus(200);
//             response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//             response.setCharacterEncoding("UTF-8");
//             response.getOutputStream().write(result.getBytes(StandardCharsets.UTF_8));
//         }
//     }
//
// }
//
//
//
