package com.nestor.springcloudnacosconsumer.rule;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 自定义权重rule
 *
 * @author : Nestor.Bian
 * @version : V 1.0
 * @date : 2020/10/8
 */
public class WeightCustomRule extends AbstractLoadBalancerRule {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeightCustomRule.class);

    @Autowired
    private NacosDiscoveryProperties nacosDiscoveryProperties;

    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {
        // 读取配置文件并且初始化,ribbon内部的 几乎用不上
    }

    @Override
    public Server choose(Object o) {
        LOGGER.info("========choose方法传入参数:[{}]========", o);
        // 获取service name
        BaseLoadBalancer loadBalancer = (BaseLoadBalancer) super.getLoadBalancer();
        String serviceName = loadBalancer.getName();

        NamingService namingService = nacosDiscoveryProperties.namingServiceInstance();
        try {
            // 随机权重策略
            Instance instance = namingService.selectOneHealthyInstance(serviceName,
                    nacosDiscoveryProperties.getGroup());
            return new NacosServer(instance);
        } catch (NacosException e) {
            LOGGER.error("自定义权重规则发生错误", e);
        }
        return null;
    }
}
