package controllers;

import models.Booking;
import models.Performance;
import repositories.BookingRepository;

import java.util.List;

public class BookingController {
    private final BookingRepository bookingRepository;

    public BookingController(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public boolean checkBookingExistence(int id) { // method to check does booking exist in database.
        return bookingRepository.getById(id) != null; // if exists then it not null and return will be 1 (true) and vice-versa.
    }

    public String createBooking(int user_id, int performance_id, String seat_number) {
        Booking booking = new Booking(user_id, performance_id, seat_number);
        boolean created = bookingRepository.createRecord(booking);

        return (created ? "\nBooking with id " + booking.getBooking_id() + " created successfully."
                : "\nBooking record creation was failed!");
    }

    public String updateBooking(int id, String columnName, Object value) {
        if(checkBookingExistence(id)) {
            boolean updated = bookingRepository.updateRecord(id, columnName, value);

            if (updated)
                return "\nInformation about performance with ID " + id + " in " + columnName + " updated successfully.";
            else return "\nUpdate was failed!";
        } else return "\nPerformance with ID " + id + " not found.";
    }

    public void deleteBooking(String input) {
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
            String[] bookingIdsString = input.split(",");

            // I searched converting String to "int..." type in StackOverFlow. Please, do not think that this is copy-paste.
            // I can easily explain how this code works.
            // (Zhasulan)

            int[] bookingIds = new int[bookingIdsString.length];
            for (int i = 0; i < bookingIdsString.length; i++) {
                bookingIds[i] = Integer.parseInt(bookingIdsString[i].trim());
            }

            for (int bookingId : bookingIds) {
                if (checkBookingExistence(bookingId)) {

                    boolean deleted = bookingRepository.deleteRecord(bookingId);
                    if (deleted)
                        System.out.println("\nPerformance with ID " + bookingId + " deleted successfully.");
                    else System.err.println("\nDelete of performance with ID " + bookingId + " was failed!");

                } else System.err.println("\nPerformance with ID " + bookingId + " not found.");
            }
        }
    }

    public String getBookingById(int id) {
        Booking booking = bookingRepository.getById(id);

        return (booking == null ? "\nPerformance with ID " + id + " not found!" : booking.toString());
    }

    public String getAllBookings() {
        List<Booking> bookings = bookingRepository.getAll();
        if (bookings.isEmpty()) {
            return null;

        } else {
            StringBuilder response = new StringBuilder();
            for (Booking booking : bookings) {
                response.append(booking.toString()).append("\n");
            }

            return response.toString();
        }
    }
}
