package com.nestor.springcloudnacosconsumer.controller;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nestor.springcloudnacosconsumer.provider.CustomRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * @author : Nestor.Bian
 * @version : V 1.0
 * @date : 2020/9/21
 */
@RestController
public class ConsumerController {

    // @NacosInjected
    // private NamingService namingService;

    // @Autowired
    // private DiscoveryClient discoveryClient;

    // @Autowired
    // private CustomRestTemplate customRestTemplate;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/info")
    public String info() throws NacosException {
        URI uri = UriComponentsBuilder.fromUriString("http://nacos-producer/producer/info").build().toUri();
        return restTemplate.getForObject(uri, String.class);
    }

    @PostMapping("/info")
    public String infoPost(@RequestBody Info info) throws NacosException {
        URI uri = UriComponentsBuilder.fromUriString("http://nacos-producer/producer/info").build().toUri();
        return restTemplate.postForObject(uri, new HttpEntity<>(info), String.class);
    }

    @GetMapping("/producer-info")
    public String producerInfo() throws NacosException {
        URI uri = UriComponentsBuilder.fromUriString("http://nacos-producer1/info").build().toUri();
        return restTemplate.getForObject(uri, String.class);
    }

    public static class Info {
        private String name;
        private String message;
        private int status;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return "Info{" + "name='" + name + '\'' + ", message='" + message + '\'' + ", status=" + status + '}';
        }
    }
}
