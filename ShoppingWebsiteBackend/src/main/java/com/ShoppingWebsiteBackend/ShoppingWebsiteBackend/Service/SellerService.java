package com.ShoppingWebsiteBackend.ShoppingWebsiteBackend.Service;

import com.ShoppingWebsiteBackend.ShoppingWebsiteBackend.Repository.ProductRepository;
import com.ShoppingWebsiteBackend.ShoppingWebsiteBackend.Repository.UserRepository;
import com.ShoppingWebsiteBackend.ShoppingWebsiteBackend.exception.AcessNotFound;
import com.ShoppingWebsiteBackend.ShoppingWebsiteBackend.exception.InvalidProductID;
import com.ShoppingWebsiteBackend.ShoppingWebsiteBackend.exception.UserNotFound;
import com.ShoppingWebsiteBackend.ShoppingWebsiteBackend.model.AppUser;
import com.ShoppingWebsiteBackend.ShoppingWebsiteBackend.model.Product;
import com.ShoppingWebsiteBackend.ShoppingWebsiteBackend.responsebody.ProductResponseBody;
import com.ShoppingWebsiteBackend.ShoppingWebsiteBackend.responsebody.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

        // We need to validate is this product belongs to the user sellerID is passed

        Product product = productService.getProductByID(productID);
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

    public List<ProductResponseBody> getAllProductsBySellerID(UUID sellerID) {
        List<Product> products = productRepository.getAllProductsBySellerID(sellerID);
        List<ProductResponseBody> productResponseBodies = new ArrayList<>();

        for(Product product : products){

            // Try model mapper to map all these automatically!
            AppUser seller = product.getSeller();
            ProductResponseBody productResponseBody = new ProductResponseBody();
            UserResponse userResponse=new UserResponse();
            // Setting product values inside the product response body
            productResponseBody.setProductName(product.getProductName());
            productResponseBody.setCategory(product.getProductCategory());
            productResponseBody.setPrice(product.getPrice());
            productResponseBody.setRating(product.getRating());
            productResponseBody.setQuantity(product.getQuantity());
            // Setting values inside the user response body.
            userResponse.setUserName(seller.getName());
            userResponse.setEmail(seller.getEmail());
            userResponse.setPhoneNumber(seller.getPhoneNumber());
            userResponse.setAddress(seller.getAddress());
            userResponse.setAge(seller.getAge());
            // Set userResponseBody inside product response body.
            productResponseBody.setUserResponse(userResponse);

            // For a specific product, product response body is built. Now we need to add each product response body into list.
            productResponseBodies.add(productResponseBody);
        }
        return productResponseBodies;
    }


    public Integer getProductTotalQuantitySoldByID(UUID sellerID, UUID productID){
        // Validate the seller
        Boolean isSeller = userService.isSeller(sellerID);
        if(isSeller == null){
            throw new UserNotFound(String.format("Seller with id: %s is not found",sellerID.toString()));
        }
        else if(!isSeller){
            throw new AcessNotFound(String.format("User with name %s is not a seller. Access denied!",
                    Objects.requireNonNull(userRepository.findById(sellerID).orElse(null)).getName()));
        }
        // Validate the product
        boolean validProduct = productService.validateProductID(productID);
        if(!validProduct){
            throw new InvalidProductID(String.format(
                    "Product with id %s does not exist in system",
                    productID.toString()
            ));
        }

        // Check if the product is owned by this sellerID
        Product product = productService.getProductByID(productID);
        AppUser owner = product.getSeller();
        if(!owner.getId().equals(sellerID)){
            throw new AcessNotFound(String.format(
                    "User with name %s does not have access to remove product %s",
                    owner.getName(),
                    product.getProductName()
            ));
        }

        return product.getTotalSoldQuantity();
    }


    public Double getProductRatings(UUID sellerID, UUID productID){
        // Validate the seller
        Boolean isSeller = userService.isSeller(sellerID);
        if(isSeller == null){
            throw new UserNotFound(String.format("Seller with id: %s is not found",sellerID.toString()));
        }
        else if(!isSeller){
            throw new AcessNotFound(String.format("User with name %s is not a seller. Access denied!",
                    Objects.requireNonNull(userRepository.findById(sellerID).orElse(null)).getName()));
        }
        // Validate the product
        boolean validProduct = productService.validateProductID(productID);
        if(!validProduct){
            throw new InvalidProductID(String.format(
                    "Product with id %s does not exist in system",
                    productID.toString()
            ));
        }

        // Check if the product is owned by this sellerID
        Product product = productService.getProductByID(productID);
        AppUser owner = product.getSeller();
        if(!owner.getId().equals(sellerID)){
            throw new AcessNotFound(String.format(
                    "User with name %s does not have access to remove product %s",
                    owner.getName(),
                    product.getProductName()
            ));
        }

        return product.getRating();
    }
}
