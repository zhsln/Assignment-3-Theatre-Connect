package models;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter

public class Performance {
    private int id;
    private String title;
    private LocalDate date;
    private int duration;
    private String venue; // venue means seat place.
}