package controllers;

import models.Performance;
import models.User;
import repositories.PerformanceRepository;

import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

public class PerformanceController {
    private final PerformanceRepository performanceRepository;

    // There is no need to import lombok to create only one constructor.
    public PerformanceController (PerformanceRepository performanceRepository) {
        this.performanceRepository = performanceRepository;
    }

    public boolean checkPerformanceExistence (int id) { // method to check does performance exist in database.
        return performanceRepository.getById(id) != null; // if exists then it not null and return will be 1 (true) and vice-versa.
    }

    public String createPerformance(String title, LocalDate date, Time time, int duration, String venue) {
        Performance performance = new Performance(title, date, time, duration, venue);
        boolean created = performanceRepository.createRecord(performance);

        return (created ? "\nPerformance " + performance.getTitle() +
                " (" + performance.getDate() + " " + performance.getTime() + ") created successfully."
                : "\nPerformance creation was failed!");
        /*
        Example, Performance Swan Lake (10-02-2024 19:30:00) created successfully.
        */
    }

    public String updatePerformance(int id, String columnName, Object value) {
        if(checkPerformanceExistence(id)) {
            boolean updated = performanceRepository.updateRecord(id, columnName, value);

            if (updated)
                return "\nInformation about performance with ID " + id + " in " + columnName + " updated successfully.";
            else return "\nUpdate was failed!";
        } else return "\nPerformance with ID " + id + " not found.";
    }

    public void deletePerformance(String input) {
        String regex1 = "^\\d+(, \\d+)*$"; // For case if user enters something like "27, 28". Separated with comma and space.
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
            String[] performanceIdsString = input.split(",");

            // I searched converting String to "int..." type in StackOverFlow. Please, do not think that this is copy-paste.
            // I can easily explain how this code works.
            // (Zhasulan)

            int[] performanceIds = new int[performanceIdsString.length];
            for (int i = 0; i < performanceIdsString.length; i++) {
                performanceIds[i] = Integer.parseInt(performanceIdsString[i].trim());
            }

            for (int performanceId : performanceIds) {
                if (checkPerformanceExistence(performanceId)) {

                    boolean deleted = performanceRepository.deleteRecord(performanceId);
                    if (deleted)
                        System.out.println("\nPerformance with ID " + performanceId + " deleted successfully.");
                    else System.err.println("\nDelete of performance with ID " + performanceId + " was failed!");

                } else System.err.println("\nPerformance with ID " + performanceId + " not found.");
            }
        }
    }

    public String getPerformanceById(int id) {
        Performance performance = performanceRepository.getById(id);

        return (performance == null ? "\nPerformance with ID " + id + " not found!" : performance.toString());
    }

    public String getAllPerformances() {
        List<Performance> performances = performanceRepository.getAll();
        if (performances.isEmpty()) {
            return null;

        } else {
            StringBuilder response = new StringBuilder();
            for (Performance performance : performances) {
                response.append(performance.toString()).append("\n");
            }

            return response.toString();
        }
    }
}
