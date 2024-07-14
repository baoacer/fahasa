package com.whilewework.fahasa.dto;

import lombok.Data;

@Data
public class ProductDto {

    private Long id;

    private String title;

    private String publisher;

    private String publisherBy;

    private String author;

    private String coverType;

    private Long price;

    private double originalPrice;

    private int discount;

    private String currency;

    private String img;

    private Long categoryId;

    private String categoryName;

    private String publishingYear;

    private String supplier;

    private String language;

    private String packagingSize;

    private int numberOfPages;

    private String headerDescription;

    private String aboutTheAuthor;

    private String description;

}
