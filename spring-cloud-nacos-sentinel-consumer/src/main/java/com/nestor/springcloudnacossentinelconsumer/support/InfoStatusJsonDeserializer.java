package com.nestor.springcloudnacossentinelconsumer.support;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.nestor.springcloudnacossentinelconsumer.enums.InfoStatus;

/**
 * @author : Nestor.Bian
 * @version : V 1.0
 * @date : 2020/10/19
 */
public class InfoStatusJsonDeserializer extends JsonDeserializer<InfoStatus> {

    private static final Logger LOGGER = LoggerFactory.getLogger(InfoStatusJsonDeserializer.class);

    @Override
    public InfoStatus deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        int asInt = jsonParser.getValueAsInt();
        LOGGER.info("InfoStatusJsonDeserializer反序列化前:[{}]", asInt);
        for (InfoStatus value : InfoStatus.values()) {
            if (value.getCode() == asInt) {
                return value;
            }
        }
        return null;
    }
}
