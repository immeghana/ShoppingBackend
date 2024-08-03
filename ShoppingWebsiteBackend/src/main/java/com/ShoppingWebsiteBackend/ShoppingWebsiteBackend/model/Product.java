package com.ShoppingWebsiteBackend.ShoppingWebsiteBackend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false,unique = true)
    private String productName;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int quantity;

    private double rating;

    @Column(nullable = false)
    private int totalSoldQuantity;

    @Column(nullable = false)
    private String productCategory;

    @ManyToOne
    AppUser seller;
}
