package com.ShoppingWebsiteBackend.ShoppingWebsiteBackend.responsebody;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserResponse {
    private String userName;
    private int age;
    private String email;
    private Long phoneNumber;
    private String address;
}
