package models;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter

public class Performance {
    private int id;
    private String title;
    private LocalDate date; // dd-MM-yyyy
    private Time time; // HH:mm:ss
    private int duration; // In minutes.
    private String venue; // Venue means place of the event.

    public Performance(String title, LocalDate date, Time time, int duration, String venue) {
        setTitle(title);
        setDate(date);
        setTime(time);
        setDuration(duration);
        setVenue(venue);
    }

    @Override
    public String toString() {
        return "==============================" +
                "\n| Performance with ID: " + id +
                "\n| Title: " + title +
                "\n| Date and Time: " + date + " " + time +
                "\n| Duration (min): " + duration +
                "\n| Venue: " + venue +
                "\n==============================";
    }

    // Method to convert date written in String to actually Date type.
    /*private Date convertStringToDate(String dateString) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date date = dateFormat.parse(dateString);

            return date;

        } catch (ParseException e) {
            ErrorHandler.handleParseException(e);
        }

        return null; // If parsing was incorrect.
    }*/

    // Method to convert time written in String to actually Time type.
    /*private Time convertStringToTime(String timeString) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        try {
            Date parsedTime = timeFormat.parse(timeString);
            Time time = new Time(parsedTime.getTime());

            return time;

        } catch (ParseException e) {
            ErrorHandler.handleParseException(e);
        }

        return null; // If parsing was incorrect.
    }*/
}