package com.ShoppingWebsiteBackend.ShoppingWebsiteBackend.Controller;

import com.ShoppingWebsiteBackend.ShoppingWebsiteBackend.Service.SellerService;
import com.ShoppingWebsiteBackend.ShoppingWebsiteBackend.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/seller")
public class SellerController {

    @Autowired
    SellerService sellerService;

    @PostMapping("/product/register")
    public void addProduct(@RequestBody Product product, @RequestParam UUID sellerID){
        sellerService.addProduct(product, sellerID);
    }
}
