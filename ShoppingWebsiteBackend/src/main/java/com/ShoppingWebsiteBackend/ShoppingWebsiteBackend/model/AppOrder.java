package com.ShoppingWebsiteBackend.ShoppingWebsiteBackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AppOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    private AppUser buyer;

    @OneToMany
    List<Product> products;

    @Column(nullable = false)
    int totalQuantity;

    @Column(nullable = false)
    int totalPrice;
}
