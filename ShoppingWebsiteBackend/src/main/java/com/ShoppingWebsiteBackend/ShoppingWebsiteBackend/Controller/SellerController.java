package com.ShoppingWebsiteBackend.ShoppingWebsiteBackend.Controller;

import com.ShoppingWebsiteBackend.ShoppingWebsiteBackend.Service.ProductService;
import com.ShoppingWebsiteBackend.ShoppingWebsiteBackend.Service.SellerService;
import com.ShoppingWebsiteBackend.ShoppingWebsiteBackend.exception.AcessNotFound;
import com.ShoppingWebsiteBackend.ShoppingWebsiteBackend.exception.InvalidProductID;
import com.ShoppingWebsiteBackend.ShoppingWebsiteBackend.exception.UserNotFound;
import com.ShoppingWebsiteBackend.ShoppingWebsiteBackend.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/seller")
public class SellerController {

    @Autowired
    SellerService sellerService;

    @Autowired
    ProductService productService;

    @PostMapping("/product/register")
    public void addProduct(@RequestBody Product product, @RequestParam UUID sellerID){
        sellerService.addProduct(product, sellerID);
    }
    @DeleteMapping("/product/remove")
    public String removeProduct(@RequestParam UUID sellerID, @RequestParam UUID productID){
        try{
            return sellerService.removeProduct(sellerID, productID);
        }catch (UserNotFound | AcessNotFound | InvalidProductID userNotFound){
            return userNotFound.getMessage();
        }
    }

}
