package com.nestor.springcloudnacosconsumer.rule;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 同集群同版本优先调用rule
 *
 * @author : Nestor.Bian
 * @version : V 1.0
 * @date : 2020/10/8
 */
public class SameClusterAndVersionPriorityRule extends AbstractLoadBalancerRule {

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
        String currentVersion = nacosDiscoveryProperties.getMetadata().get("current-version");
        try {
            Instance instance;
            // 获取同集群实例
            List<Instance> sameClusterInstances = namingService.selectInstances(serviceName,
                    nacosDiscoveryProperties.getGroup(),
                    Collections.singletonList(nacosDiscoveryProperties.getClusterName()), true);
            // 同版本判断
            List<Instance> sameClusterAndVersionInstances = sameClusterInstances.stream().filter(
                    item -> Objects.equals(item.getMetadata().get("current-version"), currentVersion)).collect(
                    Collectors.toList());
            if (!CollectionUtils.isEmpty(sameClusterAndVersionInstances)) {
                instance = WeightBalancer.getHostByRandomWeight(sameClusterAndVersionInstances);
                LOGGER.info("同集群调用相同版本! 消费者所在集群:[{}], 所在版本:[{}], 生产者所在集群:[{}], 所在版本:[{}], 生产者ip:[{}], port:[{}]",
                        nacosDiscoveryProperties.getClusterName(), currentVersion, instance.getClusterName(),
                        instance.getMetadata().get("current-version"), instance.getIp(), instance.getPort());
                return new NacosServer(instance);
            }

            // 跨集群调用
            List<Instance> crossClusterInstances = namingService.selectInstances(serviceName,
                    nacosDiscoveryProperties.getGroup(), true);
            // 同版本判断
            List<Instance> crossClusterSameVersionInstances = crossClusterInstances.stream().filter(
                    item -> Objects.equals(item.getMetadata().get("current-version"), currentVersion)).collect(
                    Collectors.toList());
            // 随机权重
            instance = WeightBalancer.getHostByRandomWeight(crossClusterSameVersionInstances);
            if (Objects.isNull(instance)) {
                LOGGER.error("在版本:[{}]下, 无可用服务", currentVersion);
                throw new RuntimeException("版本:" + currentVersion + "下, 无可用服务");
            }
            LOGGER.info("跨集群调用相同版本! 消费者所在集群:[{}], 所在版本:[{}], 生产者所在集群:[{}], 所在版本:[{}], 生产者ip:[{}], port:[{}]",
                    nacosDiscoveryProperties.getClusterName(), currentVersion, instance.getClusterName(),
                    instance.getMetadata().get("current-version"), instance.getIp(), instance.getPort());

            return new NacosServer(instance);
        } catch (NacosException e) {
            LOGGER.error("同集群同版本优先调用规则发生错误", e);
        }

        return null;
    }
}
