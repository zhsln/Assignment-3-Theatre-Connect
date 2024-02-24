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

    // Override toString() method for better representation.
    @Override
    public String toString() {
        return "\n==============================" +
                "\n| Performance with ID: " + id +
                "\n| Title: " + title +
                "\n| Date and Time: " + date + " " + time +
                "\n| Duration (min): " + duration +
                "\n| Venue: " + venue +
                "\n==============================";
    }
}
