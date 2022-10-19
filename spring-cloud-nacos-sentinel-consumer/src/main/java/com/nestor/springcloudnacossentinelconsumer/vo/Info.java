package com.nestor.springcloudnacossentinelconsumer.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nestor.springcloudnacossentinelconsumer.enums.InfoStatus;
import com.nestor.springcloudnacossentinelconsumer.support.InfoStatusJsonDeserializer;
import com.nestor.springcloudnacossentinelconsumer.support.InfoStatusJsonSerializer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : Nestor.Bian
 * @version : V 1.0
 * @date : 2020/10/19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Info {
    private String name;
    private String message;
    @JsonDeserialize(using = InfoStatusJsonDeserializer.class)
    @JsonSerialize(using = InfoStatusJsonSerializer.class)
    // @JsonFormat(shape = NUMBER_INT)
    private InfoStatus status;

    @Override
    public String toString() {
        return "Info{" + "name='" + name + '\'' + ", message='" + message + '\'' + ", status=" + status + '}';
    }
}
