package com.nestor.springcloudnacosopenfeignconsumer.support;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.DefaultPropertySourceFactory;
import org.springframework.core.io.support.EncodedResource;

/**
 * yaml properties factory
 *
 * @author : Nestor.Bian
 * @version : V 1.0
 * @date : 2020/10/29
 */
public class CommonPropertySourceFactory extends DefaultPropertySourceFactory {

    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {
        String fileName = Objects.isNull(name) ? resource.getResource().getFilename() : name;
        if (StringUtils.isNotEmpty(fileName) && (fileName.endsWith(".yml") || fileName.endsWith(".yaml"))) {
            // List<PropertySource<?>> load = new YamlPropertySourceLoader().load(fileName, resource.getResource());
            // return load.size() == 0 ? new PropertiesPropertySource(fileName, new Properties()) : load.get(0);
            YamlPropertiesFactoryBean yamlPropertiesFactoryBean = new YamlPropertiesFactoryBean();
            yamlPropertiesFactoryBean.setResources(resource.getResource());
            yamlPropertiesFactoryBean.afterPropertiesSet();
            Properties properties = yamlPropertiesFactoryBean.getObject();
            return new PropertiesPropertySource(fileName, properties);
        }

        return super.createPropertySource(name, resource);
    }
}
