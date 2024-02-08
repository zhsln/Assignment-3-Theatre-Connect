package models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

// Derived class Admin inheriting from base class User
public class Admin extends User {
    // Private attribute specific to Admin class
    private String adminLevel;

    // Constructor for Admin class with parameters including adminLevel
    public Admin(String login, String password, String name, String adminLevel) {
        // Call to the constructor of the base class User
        super(login, password, name);
        // Initialize adminLevel attribute
        setAdminLevel(adminLevel);
    }
}