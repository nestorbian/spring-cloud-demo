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
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 自定义同集群优先调用rule
 *
 * @author : Nestor.Bian
 * @version : V 1.0
 * @date : 2020/10/8
 */
public class SameClusterPriorityRule extends AbstractLoadBalancerRule {

    private static final Logger LOGGER = LoggerFactory.getLogger(SameClusterPriorityRule.class);

    @Autowired
    private NacosDiscoveryProperties nacosDiscoveryProperties;

    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }

    @Override
    public Server choose(Object o) {
        // service name
        BaseLoadBalancer loadBalancer = (BaseLoadBalancer) super.getLoadBalancer();
        String serviceName = loadBalancer.getName();

        NamingService namingService = nacosDiscoveryProperties.namingServiceInstance();
        try {
            Instance instance;
            // 获取同集群实例
            List<Instance> sameClusterInstances = namingService.selectInstances(serviceName,
                    nacosDiscoveryProperties.getGroup(),
                    Collections.singletonList(nacosDiscoveryProperties.getClusterName()), true);
            if (!CollectionUtils.isEmpty(sameClusterInstances)) {
                // 随机权重策略
                instance = namingService.selectOneHealthyInstance(serviceName, nacosDiscoveryProperties.getGroup(),
                        Collections.singletonList(nacosDiscoveryProperties.getClusterName()));
                LOGGER.info("同集群调用! 消费者所在集群:[{}], 生产者所在集群:[{}], 生产者ip:[{}], port:[{}]",
                        nacosDiscoveryProperties.getClusterName(), instance.getClusterName(), instance.getIp(),
                        instance.getPort());
            } else {
                // 跨集群调用 随机权重策略
                instance = namingService.selectOneHealthyInstance(serviceName, nacosDiscoveryProperties.getGroup());
                LOGGER.info("跨集群调用! 消费者所在集群:[{}], 生产者所在集群:[{}], 生产者ip:[{}], port:[{}]",
                        nacosDiscoveryProperties.getClusterName(), instance.getClusterName(), instance.getIp(),
                        instance.getPort());
            }
            return new NacosServer(instance);
        } catch (NacosException e) {
            LOGGER.error("同集群优先调用规则发生错误", e);
        }

        return null;
    }
}
