package filmorateapp.service.film;

import filmorateapp.model.Film;
import filmorateapp.model.validation.ValidationService;
import filmorateapp.storage.film.FilmStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
@RequestMapping("/films")
@Slf4j
public class FilmService {
    private final List<Film> films = new ArrayList<>();
    private final FilmStorage filmStorage;
    private int nextId = 0;

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    private ValidationService validationService;

    public void addFilm(Film film) {
        try {
            validationService.validate(film);
            film.setId(nextId++);
            films.add(film);
            log.info("Фильм добавлен с Айди " + film.getId());
        } catch (Exception e) {
            log.error("Ошибка добавления фильма " + e.getMessage());
        }
    }

    public void updateFilm(long id, Film film) {
        try {
            validationService.validate(film);
            for (int i = 0; i < films.size(); i++) {
                if (films.get(i).getId() == id) {
                    films.set(i, film);
                    log.info("Фильм обновлен с Айди " + film.getId());
                    break;
                }
            }
        } catch (Exception e) {
            log.error("Ошибка обновления фильма " + e.getMessage());
        }
    }

    public List<Film> getAllFilms() {
        try {
            if (!films.isEmpty()) {
                log.info("Получите Список фильмов" + films);
                return films;
            }
        } catch (Exception e) {
            log.error("Мапа пуста " + e.getMessage());
        }
        return films;
    }

    public void addLike(int filmId, int userId) {
        filmStorage.getFilmById(filmId).getLikes().add(userId);
    }

    public void deleteLike(int userId, int filmId) {
        if (filmStorage.getFilmById(filmId).getLikes().contains(userId)) {
            filmStorage.getFilmById(filmId).getLikes().remove(userId);
        }
    }

    public List<Film> getBestFilms(int count) {
        List<Film> bestFilms = filmStorage.getBestFilms(count);
        return bestFilms != null ? bestFilms : Collections.emptyList();
    }

    public Film getFilmById(long id) {
        return filmStorage.getFilmById(id);
    }
}