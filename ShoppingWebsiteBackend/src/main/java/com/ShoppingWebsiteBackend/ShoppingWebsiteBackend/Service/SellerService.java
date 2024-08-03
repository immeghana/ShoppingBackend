package com.ShoppingWebsiteBackend.ShoppingWebsiteBackend.Service;

import com.ShoppingWebsiteBackend.ShoppingWebsiteBackend.Repository.ProductRepository;
import com.ShoppingWebsiteBackend.ShoppingWebsiteBackend.Repository.UserRepository;
import com.ShoppingWebsiteBackend.ShoppingWebsiteBackend.exception.AccessNotFound;
import com.ShoppingWebsiteBackend.ShoppingWebsiteBackend.exception.UserNotFound;
import com.ShoppingWebsiteBackend.ShoppingWebsiteBackend.model.AppUser;
import com.ShoppingWebsiteBackend.ShoppingWebsiteBackend.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SellerService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;
    public String addProduct(Product product, UUID sellerID){
        // With the help of seller ID we must retrieve the seller user object.
        // If no object is found, return null
        // If the returned object is buyer then we have to throw an exception
        // Access granted if all the cases are cleared

        AppUser seller = userRepository.findById(sellerID).orElse(null);
        if(seller ==null){
            throw new UserNotFound(String.format("The user with ID: %s is not found",sellerID));
        }
        if(!seller.getUsertype().equals("SELLER")){
            throw new AccessNotFound(String.format("User with ID: %s does not have access to add product",sellerID));
        }

        product.setSeller(seller);
        productRepository.save(product);
        return "Product is saved into the database";
    }
}
