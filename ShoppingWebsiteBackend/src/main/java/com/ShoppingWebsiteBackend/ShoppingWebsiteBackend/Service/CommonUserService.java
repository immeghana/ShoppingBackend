package com.ShoppingWebsiteBackend.ShoppingWebsiteBackend.Service;

import com.ShoppingWebsiteBackend.ShoppingWebsiteBackend.Repository.UserRepository;
import com.ShoppingWebsiteBackend.ShoppingWebsiteBackend.exception.UserNotFound;
import com.ShoppingWebsiteBackend.ShoppingWebsiteBackend.exception.WrongCredentials;
import com.ShoppingWebsiteBackend.ShoppingWebsiteBackend.model.AppUser;
import com.ShoppingWebsiteBackend.ShoppingWebsiteBackend.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.UnknownServiceException;
import java.util.UUID;

@Service
public class CommonUserService {

    @Autowired
    private UserRepository userRepository;

    public String authenticateUser(String token){
        // Token is now a plain string as xyz@gmail.com:password
        // Split token on basis of colon, call repo layer to get the user by email
        // Get the user object and compare the password.
        // If password matches, return successful message else return failure message

        String[] userCredentials = token.split(":");
        String userEmail = userCredentials[0];
        String userPassword = userCredentials[1];

        // Check if the user with this email is present in the database.
        // Verify the password present in our system is equal to the password provided by the user.

        AppUser user = userRepository.findByEmail(userEmail);
        if(user == null){
            throw new UserNotFound(String.format("User with email %s does not exist in database",userEmail));
        }
        String originalPassword=user.getPassword();
        if(originalPassword.equals(userPassword)){
            return "Authentication successful";
        }
        throw new WrongCredentials("Password provided by user is incorrect");
    }

    public AppUser getUserById(UUID userID){
        return userRepository.findById(userID).orElse(null); // this will return the AppUser by UUID
    }

    public String registerUser(AppUser User){
        // Save method implementation is done by hibernate
        userRepository.save(User);
        return "User data saved into database!";
    }
}
