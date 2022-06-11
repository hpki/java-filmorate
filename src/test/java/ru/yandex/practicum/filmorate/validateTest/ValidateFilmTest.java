package ru.yandex.practicum.filmorate.validateTest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validate.FilmValidator;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidateFilmTest {

    class ValidateFilmDataTest {
        Film film = Film.builder()
                .id(1)
                .name("Name1")
                .description("Description1")
                .duration(60)
                .releaseDate(LocalDate.of(2021, 6, 5))
                .build();

        @AfterEach
        public void film() {
            film = Film.builder()
                    .id(1)
                    .name("Name1")
                    .description("Description1")
                    .duration(60)
                    .releaseDate(LocalDate.of(2021, 6, 5))
                    .build();
        }

        @Test
        public void emptyName() {
            film.setName("");
            assertFalse(new FilmValidator(film).checkAllData());
        }

        @Test
        public void tooLongDescription() {
            film.setDescription(" ".repeat(201));
            assertFalse(new FilmValidator(film).checkAllData());
        }

        @Test
        public void tooOldReleaseDate() {
            film.setReleaseDate(LocalDate.of(1894, 05, 21));
            assertFalse(new FilmValidator(film).checkAllData());
        }

        @Test
        public void negativeDuration() {
            film.setDuration(-1);
            assertFalse(new FilmValidator(film).checkAllData());
        }

        @Test
        public void correctAllData() {
            assertTrue(new FilmValidator(film).checkAllData());
        }
    }
}
