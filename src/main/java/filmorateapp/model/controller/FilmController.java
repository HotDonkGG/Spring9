package filmorateapp.model.controller;

import filmorateapp.model.Film;
import filmorateapp.service.film.FilmService;
import filmorateapp.storage.film.FilmStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Slf4j
@RequestMapping("/film")
@RequiredArgsConstructor
public class FilmController {

    private final FilmStorage filmStorage;
    private final FilmService filmService;

    /**
     * Добавление фильма
     */
    @PostMapping
    public Film addFilm(@RequestBody Film film) {
        log.info("Поступил запрос на добавление фильма.");
        return filmStorage.addFilm(film);
    }

    @PutMapping
    public Film updateFilm(@PathVariable long id, @PathVariable Film film) {
        log.info("Поступил запрос на изменения фильма.");
        return filmStorage.updateFilm(id, film);
    }

    /**
     * Пользователь ставит лайк фильму
     */
    @PutMapping("/{id}/like/{filmId}")
    public void addLike(@PathVariable String id, @PathVariable String filmId) {
        log.info("Поступил запрос на добавление лайка фильму.");
        filmService.addLike(Integer.parseInt(id), Integer.parseInt(filmId));
    }

    @GetMapping("/{id}")
    public Film getFilm(@PathVariable String id) {
        log.info("Получен GET-запрос на получение фильма");
        return filmStorage.getFilmById(Integer.parseInt(id));
    }

    /**
     * Возвращает список из первых count фильмов по количеству лайков
     */
    @GetMapping("/popular")
    public List<Film> getBestFilms(@RequestParam(defaultValue = "10") String count) {
        log.info("Поступил запрос на получение списка 10 популярных фильмов.");
        return filmService.getBestFilms(Integer.parseInt(count));
    }

    /**
     * Пользователь удаляет лайк.
     */
    @DeleteMapping("/{id}/like/{filmId}")
    public void deleteLike(@PathVariable String id, @PathVariable String filmId) {
        log.info("Поступил запрос на удаление лайка у фильма.");
        filmService.deleteLike(Integer.parseInt(filmId), Integer.parseInt(id));
    }
}