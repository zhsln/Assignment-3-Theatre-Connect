package models;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter

public class Performance {
    private int id;
    private String title;
    private LocalDateTime timestamp; /* In format YYYY-MM-DD HH:MM:SS +- Time zone.
                                    For example, 2004-10-19 10:23:54+02 (+02 is timezone). */
    private int duration; // In minutes.
    private String venue; // Venue means place of the event.

    public Performance(String title, LocalDateTime timestamp, int duration, String venue) {
        setTitle(title);
        setTimestamp(timestamp);
        setDuration(duration);
        setVenue(venue);
    }

    @Override
    public String toString() {
        return "==============================" +
                "Performance with ID: " + id +
                "\n| Title: " + title +
                "\n| Date and Time: " + timestamp +
                "\n| Duration (min): " + duration +
                "\n| Venue: " + venue +
                "\n==============================";
    }
}