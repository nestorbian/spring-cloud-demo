package com.nestor.springcloudnacosproducer.vo;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author : Nestor.Bian
 * @version : V 1.0
 * @date : 2020/11/5
 */
@Data
public class OrderVO {
    private Long id;
    private String txnNumber;
    private BigDecimal amt;
    @JsonManagedReference
    private List<ProductVO> products;
}
