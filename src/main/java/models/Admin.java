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
    private int adminLevel; /* Private attribute specific to Admin class.
                               user with adminLevel == 1 can only add, change and delete records in booking table in DB.
                               user with adminLevel == 2 can add, change and delete any records in any tables in DB. */

    public Admin(String login, String password, String name, String surname, int adminLevel) {
        super(login, password, name, surname);
        setAdminLevel(adminLevel);
    }

    @Override
    public String toString() { // This is the best toString() I've ever done... (Zhasulan)
        return "\n==============================" +
                "\n| Admin with ID: " + getId() +
                "\n| Full name: " + getName() + " " + getSurname() +
                "\n| Login: " + getLogin() +
                "\n| Password: " + getPassword() +
                "\n==============================";
    }
}