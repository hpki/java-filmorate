package ru.yandex.practicum.filmorate.controller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.validate.ValidateFilm;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;

@RestController
@Slf4j
public class FilmController {

    private final HashMap<Long, Film> films = new HashMap<>();

    private static long id = 0;

    @GetMapping("/films")
    public Collection<Film> findAllFilms() {
        return films.values();
    }

    @PostMapping("/films")
    @ResponseBody
    public Film createFilm(@RequestBody Film film) {
        if(new ValidateFilm(film).checkAllData()) {
            log.info("Получен запрос к эндпоинту: POST /films");
            film.setId(getId());
            films.put(film.getId(), film);
            return films.get(film.getId());
        } else {
            log.warn("Запрос к эндпоинту POST не обработан. Введеные данные о фильме не удовлетворяют условиям");
            throw new ValidationException("Одно или несколько из условий не выполняются.");
        }
    }

    @PutMapping
    public Film update(@RequestBody Film film) {
        if(new ValidateFilm(film).checkAllData() && film.getId() > 0) {
            log.info("Получен запрос к эндпоинту: PUT /films");
            films.put(film.getId(), film);
            return films.get(film.getId());
        } else {
            throw new ValidationException("Одно или несколько из условий не выполняются.");
        }
    }

    public long getId() {
        this.id++;
        return id;
    }


}
