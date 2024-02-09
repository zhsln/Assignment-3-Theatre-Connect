package models;

import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Getter;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter

// Class representing a User entity.
public class User {
    private int id;
    private String login;
    private String password;
    private String name;
    private String surname;

    // Arguments constructor (without id).
    public User(String login, String password, String name, String surname) {
        setLogin(login);
        setPassword(password);
        setName(name);
        setSurname(surname);
    }

    @Override
    public String toString() {
        return "==============================" +
                "\n| User with ID: " + id +
                "\n| Full name: " + name + " " + surname +
                "\n| Login: " + login +
                "\n| Password: " + password +
                "\n==============================";
    }
}