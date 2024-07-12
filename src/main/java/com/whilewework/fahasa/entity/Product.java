package com.whilewework.fahasa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.whilewework.fahasa.dto.ProductDto;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "product")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] images;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Category category;

    public ProductDto getDto() {
        ProductDto productDto = new ProductDto();
        productDto.setId(id);
        productDto.setTitle(title);
        productDto.setPublisher(publisher);
        productDto.setPublisherBy(publisherBy);
        productDto.setAuthor(author);
        productDto.setCoverType(coverType);
        productDto.setPrice(price);
        productDto.setOriginalPrice(originalPrice);
        productDto.setDiscount(discount);
        productDto.setCurrency(currency);
        productDto.setByteImg(images);
        productDto.setCategoryId(category.getId());
        productDto.setCategoryName(category.getName());
        return productDto;
    }
}
