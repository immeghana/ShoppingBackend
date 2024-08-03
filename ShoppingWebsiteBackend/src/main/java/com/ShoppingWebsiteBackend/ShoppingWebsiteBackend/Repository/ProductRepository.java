package com.ShoppingWebsiteBackend.ShoppingWebsiteBackend.Repository;

import com.ShoppingWebsiteBackend.ShoppingWebsiteBackend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    @Query(value = "select * from product where seller_id =:sellerID", // Use your dynamic variable after colon
    nativeQuery = true) // Use your own native Query instead of JPA query
    public List<Product> getAllProductsBySellerID(UUID sellerID);
}
