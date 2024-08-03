package com.ShoppingWebsiteBackend.ShoppingWebsiteBackend.Repository;

import com.ShoppingWebsiteBackend.ShoppingWebsiteBackend.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<AppUser, UUID> {
    // Extends JpaRepository will give us the access to methods predefined in JpaRepository class,
    // which will be helpful for the implementation of the project.

    // These methods will be able to find the user through the attributes of the user class.
    public AppUser findByEmail(String email);

    // These methods will be implemented by JPA
    public AppUser findByphoneNumber(Long phoneNumber);

}
