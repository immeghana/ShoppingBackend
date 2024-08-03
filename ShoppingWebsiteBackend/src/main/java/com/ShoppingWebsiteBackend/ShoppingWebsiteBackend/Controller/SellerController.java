package com.ShoppingWebsiteBackend.ShoppingWebsiteBackend.Controller;

import com.ShoppingWebsiteBackend.ShoppingWebsiteBackend.Service.ProductService;
import com.ShoppingWebsiteBackend.ShoppingWebsiteBackend.Service.SellerService;
import com.ShoppingWebsiteBackend.ShoppingWebsiteBackend.exception.AcessNotFound;
import com.ShoppingWebsiteBackend.ShoppingWebsiteBackend.exception.InvalidProductID;
import com.ShoppingWebsiteBackend.ShoppingWebsiteBackend.exception.UserNotFound;
import com.ShoppingWebsiteBackend.ShoppingWebsiteBackend.model.Product;
import com.ShoppingWebsiteBackend.ShoppingWebsiteBackend.responsebody.ProductResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    // Delete any product of particular user
    @DeleteMapping("/product/remove")
    public String removeProduct(@RequestParam UUID sellerID, @RequestParam UUID productID){
        try{
            return sellerService.removeProduct(sellerID, productID);
        }catch (UserNotFound | AcessNotFound | InvalidProductID userNotFound){
            return userNotFound.getMessage();
        }
    }

    // View all products of any single owner
    @GetMapping("/product/all")
    public List<ProductResponseBody> getAllProducts(@RequestParam UUID sellerID){
        return sellerService.getAllProductsBySellerID(sellerID);
    }

    // Get analytics of the product
    // If the string analytics as any particular field, it will return that particular analytics.
    // Analytics that are supported for now "TOTALQUANTITYSOLD", "RATINGS".
    @GetMapping("/product/analytics")
    public ResponseEntity getAnalyticsOfProduct(@RequestParam UUID sellerID,
                                                @RequestParam UUID productID,
                                                @RequestParam String analytics){

        try{
            if(analytics.equals("TOTALQUANTITYSOLD")){
                return new ResponseEntity(sellerService.getProductTotalQuantitySoldByID(sellerID,productID), HttpStatus.OK);
            }
            else if(analytics.equals("RATINGS")){
                return new ResponseEntity(sellerService.getProductRatings(sellerID,productID),HttpStatus.OK);
            }
            else{
                return new ResponseEntity("Invalid analytics request",HttpStatus.OK);
            }
        }catch (UserNotFound | AcessNotFound | InvalidProductID exception){
            return new ResponseEntity(exception.getMessage(),HttpStatus.OK);
        }

    }

}
