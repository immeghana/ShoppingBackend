package com.ShoppingWebsiteBackend.ShoppingWebsiteBackend.Service;

import com.ShoppingWebsiteBackend.ShoppingWebsiteBackend.Repository.ProductRepository;
import com.ShoppingWebsiteBackend.ShoppingWebsiteBackend.Repository.UserRepository;
import com.ShoppingWebsiteBackend.ShoppingWebsiteBackend.exception.AcessNotFound;
import com.ShoppingWebsiteBackend.ShoppingWebsiteBackend.exception.InvalidProductID;
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
    CommonUserService userService;

    @Autowired
    ProductService productService;

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
            throw new AcessNotFound(String.format("User with ID: %s does not have access to add product",sellerID));
        }

        product.setSeller(seller);
        productRepository.save(product);
        return "Product is saved into the database";
    }
    public String removeProduct(UUID sellerID, UUID productID){
        Boolean isSeller = userService.isSeller(sellerID);
        if(isSeller == null){
            throw new UserNotFound(String.format(
                    "User with id %s does not exist",
                    sellerID.toString()
            ));
        }

        if(!isSeller){
            throw new AcessNotFound(String.format(
                    "User with id %s does not have access to delete product",
                    sellerID.toString()
            ));
        }

        boolean validProduct = productService.validateProductID(productID);
        if(!validProduct){
            throw new InvalidProductID(String.format(
                    "Product with id %s does not exist in system",
                    productID.toString()
            ));
        }

        // We need to validate is this product belongs to the user whoose sellerID is passed

        Product product = productService.getProductByID(productID);
        System.out.println(product);
        AppUser owner = product.getSeller();

        if(!owner.getId().equals(sellerID)){
            throw new AcessNotFound(String.format(
                    "User with name %s does not have access to remove product %s",
                    owner.getName(),
                    product.getProductName()
            ));
        }


        productService.removeProduct(product);

        return String.format(
                "Seller with name %s removed product with id %s",
                owner.getName(),
                product.getProductName()
        );
    }
}
