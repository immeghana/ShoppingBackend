package com.ShoppingWebsiteBackend.ShoppingWebsiteBackend.responsebody;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductResponseBody {
    private String productName;
    private int price;
    private int quantity;
    private Double rating;
    private String category;
    private UserResponse userResponse;
}
