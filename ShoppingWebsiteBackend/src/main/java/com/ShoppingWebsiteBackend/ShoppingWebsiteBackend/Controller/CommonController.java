package com.ShoppingWebsiteBackend.ShoppingWebsiteBackend.Controller;

import com.ShoppingWebsiteBackend.ShoppingWebsiteBackend.Service.CommonUserService;
import com.ShoppingWebsiteBackend.ShoppingWebsiteBackend.exception.UserNotFound;
import com.ShoppingWebsiteBackend.ShoppingWebsiteBackend.exception.WrongCredentials;
import com.ShoppingWebsiteBackend.ShoppingWebsiteBackend.model.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.ServerRequest;

@RestController
@RequestMapping("/api/user")
public class CommonController {
    // will handle the endpoints of all general operations which are common to buyer and seller
    //E.g. Login and signup
    @Autowired
    CommonUserService commonUserService;
    @GetMapping("/login")
    public String authenticateUser(@RequestHeader String token){
        try{
            return commonUserService.authenticateUser(token);
        }
        // Handling both possible exceptions.
        catch(WrongCredentials | UserNotFound wrongCredentials){
            return wrongCredentials.getLocalizedMessage();
        }
    }

    //implement encryption and decryption for receiving creds from frontend
    @PostMapping("/register")
    public void registerUser(@RequestBody AppUser appuser){
        commonUserService.registerUser(appuser);
    }

}