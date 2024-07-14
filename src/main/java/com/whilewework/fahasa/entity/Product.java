package com.whilewework.fahasa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.whilewework.fahasa.dto.ProductDto;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Set;

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

    private Long price;

    private double originalPrice;

    private int discount;

    private String currency;

    private String publishingYear;

    private String supplier;

    private String language;

    private String packagingSize;

    private int numberOfPages;

    private String headerDescription;

    @Lob
    private String aboutTheAuthor;

    @Lob
    private String description;

    private String images;

    private int rating;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Comment> comments;


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
        productDto.setImg(images);
        productDto.setCategoryId(category.getId());
        productDto.setCategoryName(category.getName());
        productDto.setPublishingYear(publishingYear);
        productDto.setSupplier(supplier);
        productDto.setLanguage(language);
        productDto.setPackagingSize(packagingSize);
        productDto.setNumberOfPages(numberOfPages);
        productDto.setHeaderDescription(headerDescription);
        productDto.setAboutTheAuthor(aboutTheAuthor);
        productDto.setDescription(description);
        return productDto;
    }
}
