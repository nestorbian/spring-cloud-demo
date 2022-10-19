package com.nestor.springcloudnacosproducer.vo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author : Nestor.Bian
 * @version : V 1.0
 * @date : 2020/11/5
 */
@Data
public class ProductVO {
    private Long id;
    private String name;
    private BigDecimal amt;
    private Integer quantity;
    @JsonBackReference
    private OrderVO orderVO;
}
