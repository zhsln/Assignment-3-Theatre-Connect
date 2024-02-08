package models;

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

    // Getter method for retrieving adminLevel
    public String getAdminLevel() {
        return adminLevel;
    }

    // Setter method for updating adminLevel
    public void setAdminLevel(String adminLevel) {
        this.adminLevel = adminLevel;
    }
}