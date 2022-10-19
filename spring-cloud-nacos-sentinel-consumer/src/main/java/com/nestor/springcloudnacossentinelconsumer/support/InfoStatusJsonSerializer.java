package com.nestor.springcloudnacossentinelconsumer.support;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.nestor.springcloudnacossentinelconsumer.enums.InfoStatus;

/**
 * @author : Nestor.Bian
 * @version : V 1.0
 * @date : 2020/10/20
 */
public class InfoStatusJsonSerializer extends JsonSerializer<InfoStatus> {
    @Override
    public void serialize(InfoStatus infoStatus, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeObject(infoStatus.getCode());
    }
}
