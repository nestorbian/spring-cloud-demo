package com.nestor.springcloudnacossentinelconsumer.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author : Nestor.Bian
 * @version : V 1.0
 * @date : 2021/4/29
 */
@Data
@AllArgsConstructor
public class Result<T> {

    private String code;

    private T data;

}
