package com.nestor.springcloudnacosconsumer.provider;

import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

/**
 * 实现自定义的随机访问的ribbon RestTemplate
 *
 * @author : Nestor.Bian
 * @version : V 1.0
 * @date : 2020/9/29
 */
// @Component
public class CustomRestTemplate extends RestTemplate {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomRestTemplate.class);

    @Autowired
    private DiscoveryClient discoveryClient;

    @Override
    public <T> T doExecute(URI url, @Nullable HttpMethod method, @Nullable RequestCallback requestCallback,
                           @Nullable ResponseExtractor<T> responseExtractor) throws RestClientException {
        Assert.notNull(url, "URI is required");
        Assert.notNull(method, "HttpMethod is required");
        ClientHttpResponse response = null;
        try {
            try {
                url = buildUrl(url);
            } catch (URISyntaxException e) {
                throw new RuntimeException("url构建失败");
            }
            ClientHttpRequest request = createRequest(url, method);
            if (requestCallback != null) {
                requestCallback.doWithRequest(request);
            }
            response = request.execute();
            handleResponse(url, method, response);
            return (responseExtractor != null ? responseExtractor.extractData(response) : null);
        } catch (IOException ex) {
            String resource = url.toString();
            String query = url.getRawQuery();
            resource = (query != null ? resource.substring(0, resource.indexOf('?')) : resource);
            throw new ResourceAccessException(
                    "I/O error on " + method.name() + " request for \"" + resource + "\": " + ex.getMessage(), ex);
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

    private URI buildUrl(URI url) throws URISyntaxException {
        String serviceName = url.getHost();
        LOGGER.info("url: [{}]中服务名称: [{}]", url.toString(), serviceName);
        String urlPath = url.toString();
        List<ServiceInstance> instances = discoveryClient.getInstances(serviceName);
        if (instances.size() == 0) {
            throw new RuntimeException("未发现可用服务");
        }
        // 随机
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int index = random.nextInt(instances.size());
        String newUrl = Pattern.compile(serviceName, Pattern.LITERAL).matcher(urlPath).replaceFirst(
                instances.get(index).getHost() + ":" + instances.get(index).getPort());
        LOGGER.info("重新构建后的url: [{}]", newUrl);
        return new URI(newUrl);
    }

}
