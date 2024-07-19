package com.whilewework.fahasa.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderDto {

    private String fullName;
    private String fullAddress;
    private String contactNumber;
    private List<OrderProduct> orderProducts;

}
