package ru.yandex.practicum.filmorate.controller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.validate.FilmValidator;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@Slf4j
public class FilmController {

    private final HashMap<Long, Film> films = new HashMap<>();

    private static long id = 0;

    @GetMapping("/films")
    public List<Film> findAllFilms() {
        return new ArrayList<>(films.values());
    }

    @PostMapping("/films")
    @ResponseBody
    public ResponseEntity<Film> createFilm(@RequestBody Film film) {
        if(new FilmValidator(film).checkAllData()) {
            log.info("Получен запрос к эндпоинту: POST /films");
            film.setId(getId());
            films.put(film.getId(), film);
            return new ResponseEntity<>(film, HttpStatus.CREATED);
        } else {
            log.warn("Запрос к эндпоинту POST не обработан. Введеные данные о фильме не удовлетворяют условиям");
            throw new ValidationException("Одно или несколько из условий не выполняются.");
        }
    }

    @PutMapping("/films")
    @ResponseBody
    public ResponseEntity<Film> update(@RequestBody Film film) {
        if(new FilmValidator(film).checkAllData() && film.getId() > 0) {
            log.info("Получен запрос к эндпоинту: PUT /films");
            films.put(film.getId(), film);
            return new ResponseEntity<>(film, HttpStatus.OK);
        } else {
            throw new ValidationException("Одно или несколько из условий не выполняются.");
        }
    }

    public long getId() {
        this.id++;
        return id;
    }


}
