package com.whilewework.fahasa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;
import java.util.Set;

@Entity
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
    private String publishingYear;
    private String supplier;
    private String language;
    private String packagingSize;
    private int numberOfPages;
    private String headerDescription;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String aboutTheAuthor;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;

    private String img;
    private int rating;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Comment> comments;

    @ManyToMany(mappedBy = "products")
    @JsonIgnore
    private List<Order> orders;

}
