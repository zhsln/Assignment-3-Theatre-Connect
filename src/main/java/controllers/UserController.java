package controllers;

import models.User;
import repositories.UserRepository;

import java.util.List;

public class UserController {
    private final UserRepository userRepository;

    // There is no need to import lombok to create only one constructor.
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean checkUserExistence(int id) { // method to check does user exist in database.
        return userRepository.getById(id) != null; // if exists then it not null and return will be 1 (true) and vice-versa.
    }
    public String createUser(String login, String password, String name, String surname) {
        User user = new User(login, password, name, surname);
        boolean created = userRepository.createRecord(user);

        return (created ? "\nUser " + user.getName() + " " + user.getSurname() + " created successfully."
                        : "\nUser creation was failed!");
    }

    public String updateUser(int id, String columnName, Object value) {
        if (userRepository.getById(id) != null) {
            boolean updated = userRepository.updateRecord(id, columnName, value);

            if (updated)
                return "\nUser's information with id " + id + " in " + columnName + " updated successfully.";
            else return "\nUpdate was failed!";

        } else return "\nUser with ID " + id + " not found.";
    }
    public void deleteUser(String input) {
        String[] userIdsString = input.split(",");

        // I searched converting String to "int..." type in StackOverFlow. Please, do not think that this is copy-paste.
        // I can easily explain how this code works.
        // (Zhasulan)

        int[] userIds = new int[userIdsString.length];
        for (int i = 0; i < userIdsString.length; i++) {
            userIds[i] = Integer.parseInt(userIdsString[i].trim());
        }

        for (int userId : userIds) {
            if(checkUserExistence(userId)) {

                boolean deleted = userRepository.deleteRecord(userId);
                if (deleted)
                    System.out.println("\nUser with ID " + userId + " deleted successfully.");
                else System.out.println("\nDelete of user with ID " + userId + " was failed!");

            } else System.out.println("\nUser with ID " + userId + " not found.");
        }
    }
    public String getUserById(int id) {
        User user = userRepository.getById(id);

        return (user == null ? "\nUser with ID " + id + " not found!" : user.toString());
    }

    public String getAllUsers() {
        List<User> users = userRepository.getAll();
        if (users.isEmpty()) {
            System.out.println("\nThere is no users in database.");
            return null;

        } else {
            StringBuilder response = new StringBuilder();
            for (User user : users) {
                response.append(user.toString()).append("\n");
            }

            return response.toString();
        }
    }
}
