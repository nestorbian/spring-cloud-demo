package com.nestor.springcloudnacoshystrixconsumer.common;

import java.lang.annotation.Annotation;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * FeignClient打印耗时切面类
 *
 * @author : Nestor.Bian
 * @version : V 1.0
 * @date : 2021/6/1
 */
@Component
@Slf4j
public class FeignClientLogAdvisor extends AbstractPointcutAdvisor {
    @Override
    public Pointcut getPointcut() {
        return new Pointcut() {
            @Override
            public ClassFilter getClassFilter() {
                return clazz -> existAnnotation(clazz, FeignClient.class);
            }

            @Override
            public MethodMatcher getMethodMatcher() {
                return MethodMatcher.TRUE;
            }
        };
    }

    private boolean existAnnotation(Class<?> clazz, Class<? extends Annotation> annotationClass) {
        boolean result = clazz.isAnnotationPresent(annotationClass);
        if (!result) {
            if (clazz.getSuperclass() != null) {
                result = existAnnotation(clazz.getSuperclass(), annotationClass);
            }
        }
        if (!result) {
            Class<?>[] interfaces = clazz.getInterfaces();
            for (Class<?> item : interfaces) {
                result = existAnnotation(item, annotationClass);
                if (result) {
                    break;
                }
            }
        }
        if (!result) {
            Annotation[] annotations = clazz.getAnnotations();
            for (Annotation annotation : annotations) {
                if (clazz.isAssignableFrom(annotation.getClass())) {
                    result = true;
                    break;
                }
            }
        }

        if (result) {
            log.info("FeignClient切面作用于类:[{}]", clazz.getName());
        }

        return result;
    }

    @Override
    public Advice getAdvice() {
        return (MethodInterceptor) methodInvocation -> {
            long start = System.currentTimeMillis();
            Object proceed = methodInvocation.proceed();
            log.info("类:[{}]方法:[{}]耗时:[{}]ms", methodInvocation.getMethod().getDeclaringClass().getName(), methodInvocation.getMethod().getName(), System.currentTimeMillis() - start);
            return proceed;
        };
    }
}
