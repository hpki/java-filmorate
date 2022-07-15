package ru.yandex.practicum.filmorate.model;

import lombok.*;

@Data
@AllArgsConstructor
@Builder
public class Genre {
    private final long id;
    private final String name;

}

