package ru.yandex.practicum.filmorate.validate;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

@Slf4j
public class FilmValidator {

    private Film film;
    private static final int maxLengthDescription = 200;
    private static final LocalDate dateFirstFilm = LocalDate.of(1895, 12, 28);

    public FilmValidator(Film film) {
        this.film = film;
    }

    private boolean checkName() {
        if (!film.getName().isBlank()) {
            return true;
        } else {
            log.warn("Ошибка ввода. Название фильма не должно быть пустым");
            return false;
        }
    }

    private boolean checkLengthDescription() {
        if (film.getDescription().length() <= maxLengthDescription) {
            return true;
        } else {
            log.warn("Ошибка ввода. Описание должно быть меньше 200 символов");
            return false;
        }
    }

    private boolean checkReleaseDate() {
        if (film.getReleaseDate().isAfter(dateFirstFilm)) {
            return true;
        } else {
            log.warn("Ошибка ввода даты релиза. Релиз должен быть позже {}", dateFirstFilm);
            return false;
        }
    }

    private boolean checkDuration() {
        if (film.getDuration() > 0) {
            return true;
        } else {
            log.warn("Ошибка ввода данных. Продолжительность фильма должна быть положительной.");
            return false;
        }
    }

    public boolean checkAllData() {
        return (checkName() && checkLengthDescription() && checkReleaseDate() && checkDuration());
    }

}
