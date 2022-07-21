package ru.yandex.practicum.filmorate.storage;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.mapper.FilmRowMapper;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Component
@Qualifier("dataBase")
@AllArgsConstructor
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;
    private final GenreStorage genreStorage;

    private static final String addSqlQuery = "INSERT INTO films(name, description, release_date, duration, mpa_id)\n" +
            "VALUES (?, ?, ?, ?, ?)";
    private static final String updateSqlQuery = "UPDATE films\n" +
            "SET name= ?,\n" +
            "    description = ?,\n" +
            "    release_date = ?,\n" +
            "    duration = ?,\n" +
            "    mpa_id = ?" +
            "WHERE id = ?";

    private static final String getFilmsSqlQuery = "SELECT *\n" +
            "FROM films f\n" +
            "LEFT JOIN mpa m ON m.id = f.mpa_id";
    private static final String getFilmSqlQuery = "SELECT * FROM films f LEFT JOIN mpa m ON m.id = f.mpa_id WHERE f.id = ?";
    private static final String putLikeSqlQuery = "INSERT INTO likes(film_id, user_id) VALUES (?, ?)";
    private static final String deleteLikeSqlQuery = "DELETE FROM likes WHERE film_id = ? AND user_id = ?";
    private static final String getPopularSqlQuery = "SELECT * " +
            "FROM films AS f " +
            "LEFT JOIN likes AS l ON f.id = l.film_id " +
            "GROUP BY f.id " +
            "ORDER BY COUNT(l.user_id) DESC " +
            "LIMIT ?";


    @Override
    public Film add(Film film) {

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(addSqlQuery, new String[]{"id"});
            stmt.setString(1, film.getName());
            stmt.setString(2, film.getDescription());
            stmt.setDate(3, java.sql.Date.valueOf(film.getReleaseDate()));
            stmt.setInt(4, film.getDuration());
            stmt.setInt(5, film.getMpa().getId());
            return stmt;
        }, keyHolder);

        film.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());

        return film;
    }

    @Override
    public Film update(Film film) {

        jdbcTemplate.update(updateSqlQuery,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId(),
                film.getId());

        return film;
    }

    @Override
    public List<Film> getFilms() {

        return jdbcTemplate.query(getFilmsSqlQuery, new FilmRowMapper());
    }

    @Override
    public Optional<Film> getFilm(Long id) {

        Set<Genre> genres = genreStorage.getFilmGenres(id);
        Optional<Film> film = jdbcTemplate.query(getFilmSqlQuery, new FilmRowMapper(), id)
                .stream().findFirst();

        if (film.isPresent() & !genres.isEmpty()) {
            film.get().setGenres(genres);
        }

        return film;
    }

    @Override
    public void putLike(Film film, User user) {
        jdbcTemplate.update(putLikeSqlQuery, film.getId(), user.getId());
    }

    @Override
    public void deleteLike(Film film, User user) {
        jdbcTemplate.update(deleteLikeSqlQuery, film.getId(), user.getId());
    }

    @Override
    public List<Film> getPopular(int count) {
        return jdbcTemplate.query(getPopularSqlQuery, new FilmRowMapper(), count);
    }

}