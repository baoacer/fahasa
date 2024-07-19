package com.whilewework.fahasa.entity;

import com.whilewework.fahasa.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "orders")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderFullName;
    private String orderAddress;
    private String orderContactNumber;
    private String orderStatus;
    private double orderAmount;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @ManyToMany
    @JoinTable(
            name = "order_product",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products = new ArrayList<>();
}
