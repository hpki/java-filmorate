package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@Builder
public class Film {

    private long id; // целочисленный идентификатор
    private String name; // название
    private String description; // описание
    private LocalDate releaseDate; // дата релиза
    private int duration; // продолжительность
}
