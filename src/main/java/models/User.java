package models;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

// Class representing a User entity
public class User {
    // Private fields for user id, username, and password
    private int id;
    private String login;
    private String password;
    private String name;

    // Arguments constructor to initialize username and password
    public User(String login, String password, String name) {
        setLogin(login);
        setPassword(password);
        setName(name);
    }

    @Override
    public String toString() {
        return "User: " +
                "id >>> " + id +
                ", \nname >>> " + name +
                ", \nlogin >>> " + login +
                ", \npassword >>> " + password;
    }
}