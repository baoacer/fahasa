package com.whilewework.fahasa.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProductDto {

    private Long id;

    private String title;

    private String publisher;

    private String publisherBy;

    private String author;

    private String coverType;

    private double price;

    private double originalPrice;

    private int discount;

    private String currency;

    private byte[] byteImg;

    private Long categoryId;

    private String categoryName;

    private MultipartFile img;


}
