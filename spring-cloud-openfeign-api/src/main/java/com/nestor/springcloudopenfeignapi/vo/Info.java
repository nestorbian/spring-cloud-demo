package com.nestor.springcloudopenfeignapi.vo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nestor.springcloudopenfeignapi.enums.InfoStatus;
import com.nestor.springcloudopenfeignapi.support.InfoStatusJsonDeserializer;
import com.nestor.springcloudopenfeignapi.support.InfoStatusJsonSerializer;
import lombok.Data;

/**
 * @author : Nestor.Bian
 * @version : V 1.0
 * @date : 2020/10/19
 */
@Data
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
