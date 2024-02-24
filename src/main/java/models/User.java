package models;

import lombok.NoArgsConstructor;
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
    private Boolean editor;
    private Boolean manager;

    // Arguments constructor (without id).
    public User(String login, String password, String name, String surname, boolean editor, boolean manager) {
        setLogin(login);
        setPassword(password);
        setName(name);
        setSurname(surname);
        setEditor(editor);
        setManager(manager);
    }

    // Override toString() method for better representation.
    @Override
    public String toString() {
        String text =
                "\n==============================" +
                "\n| User with ID: " + id +
                "\n| Full name: " + name + " " + surname +
                "\n| Login: " + login;

        if (editor)
            text += "\n| This user is editor.";
        if (manager)
            text += "\n| This user is manager.";
        else if (!editor & !manager)
            text += "\n| Password: " + password;

        return text + "\n==============================";
    }
}
