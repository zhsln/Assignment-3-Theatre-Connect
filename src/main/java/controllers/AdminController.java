package controllers;

import models.Admin;
import repositories.AdminRepository;

import java.util.List;

public class AdminController {
    private final AdminRepository adminRepository;

    // There is no need to import lombok to create only one constructor.
    public AdminController(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public boolean checkAdminExistence(int id) { // method to check does admin exist in database.
        return adminRepository.getById(id) != null; // if exists then it not null and return will be 1 (true) and vice-versa.
    }
    
    public String createAdmin(String login, String password, String name, String surname, int adminLevel) {
        Admin admin = new Admin(login, password, name, surname, adminLevel);
        boolean created = adminRepository.createRecord(admin);

        return (created ? "\nAdmin " + admin.getName() + " " + admin.getSurname() + " created successfully."
                        : "\nAdmin creation was failed!");
    }

    public String updateAdmin(int id, String columnName, Object value) {
        if (checkAdminExistence(id)) {
            boolean updated = adminRepository.updateRecord(id, columnName, value);

            if (updated)
                return "\nAdmin's information with ID " + id + " in " + columnName + " updated successfully.";
            else return "\nUpdate was failed!";

        } else return "\nAdmin with ID " + id + " not found.";
    }
    
    public void deleteAdmin(String input) {
        String regex1 = "^\\d+(, \\d+)*$"; // For case if user enters something like "27, 28". Separated with comma and space
        String regex2 = "^\\d+(,\\d+)*$"; // For case if user enters something like "27,28". Separated only comma.
         /*
         I searched this in StackOverFlow. Please forgive me. (Zhasulan)
                ^ - start of the string
                \\d+ - one or more digits
                (,\\d+)* - a comma followed by one or more digits, repeated zero or more times
                $ - end of the string
         */

        if (!input.matches(regex1) & !input.matches(regex2)) {
            System.err.println("\nIncorrect input. Enter IDs (or one ID) separated by comma.");
        }
        else {
            String[] adminIdsString = input.split(",");

            // I searched converting String to "int..." type in StackOverFlow. Please, do not think that this is copy-paste.
            // I can easily explain how this code works.
            // (Zhasulan)

            int[] adminIds = new int[adminIdsString.length];
            for (int i = 0; i < adminIdsString.length; i++) {
                adminIds[i] = Integer.parseInt(adminIdsString[i].trim());
            }

            for (int adminId : adminIds) {
                if (checkAdminExistence(adminId)) {

                    boolean deleted = adminRepository.deleteRecord(adminId);
                    if (deleted)
                        System.out.println("\nAdmin with ID " + adminId + " deleted successfully.");
                    else System.out.println("\nDelete of admin with ID " + adminId + " was failed!");

                } else System.out.println("\nAdmin with ID " + adminId + " not found.");
            }
        }
    }
    
    public String getAdminById(int id) {
        Admin admin = adminRepository.getById(id);

        return (admin == null ? "\nAdmin with ID " + id + " not found!" : admin.toString());
    }

    public String getAllAdmins() {
        List<Admin> admins = adminRepository.getAll();
        if (admins.isEmpty()) {
            return null;

        } else {
            StringBuilder response = new StringBuilder();
            for (Admin admin : admins) {
                response.append(admin.toString()).append("\n");
            }

            return response.toString();
        }
    }
}
