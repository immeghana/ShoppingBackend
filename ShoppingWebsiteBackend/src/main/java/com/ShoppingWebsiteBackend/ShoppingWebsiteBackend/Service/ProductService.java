package com.ShoppingWebsiteBackend.ShoppingWebsiteBackend.Service;


import com.ShoppingWebsiteBackend.ShoppingWebsiteBackend.Repository.ProductRepository;
import com.ShoppingWebsiteBackend.ShoppingWebsiteBackend.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public Product getProductByID(UUID productID){
        return productRepository.findById(productID).orElse(null);
    }

    public boolean validateProductID(UUID productID){
        Product product = getProductByID(productID);
        return product != null;
    }

    public void removeProduct(Product product){
        productRepository.delete(product);
    }
}