package models;

import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Getter;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter

// Class representing a User entity
public class User {
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
        return "==============================" +
                "\n| User with ID: " + id +
                "\n| Name: " + name +
                "\n| Login: " + login +
                "\n| Password: " + password +
                "\n==============================";
    }
}