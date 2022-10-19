package com.nestor.springcloudnacosconsumer.rule;

import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.client.naming.core.Balancer;

import java.util.List;

/**
 * 自定义权重均衡器
 *
 * @author : Nestor.Bian
 * @version : V 1.0
 * @date : 2020/10/8
 */
public class WeightBalancer extends Balancer {

    public static Instance getHostByRandomWeight(List<Instance> hosts) {
        return Balancer.getHostByRandomWeight(hosts);
    }

}
