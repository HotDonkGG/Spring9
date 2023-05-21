package filmorateapp.model.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import filmorateapp.model.Film;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import filmorateapp.service.ValidationService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private final List<Film> films = new ArrayList<>();
    private int nextId = 0;
    @Autowired
    private ValidationService validationService;
    private FilmController filmService;

    @PostMapping
    public Film addFilm(@RequestBody Film film) {
        try {
            validationService.validate(film);
            film.setId(nextId++);
            films.add(film);
            log.info("Фильм добавлен с Айди " + film.getId());
        } catch (Exception e) {
            log.error("Ошибка добавления фильма " + e.getMessage());
        }
        return film;
    }

    @PutMapping
    public Film updateFilm(@PathVariable int id, @RequestBody Film film) {
        try {
            for (int i = 0; i < films.size(); i++) {
                if (films.get(i).getId() == id) {
                    validationService.validate(film);
                    films.set(i, film);
                    log.info("Фильм с Айди" + id + " обновлён");
                    return film;
                }
            }
        } catch (Exception e) {
            log.error("Ошибка обновления фильма " + e.getMessage());
        }
        return film;
    }

    @GetMapping
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

    @PutMapping("/{id}/like/{userId}")
    public ResponseEntity<String> addLike(@PathVariable long id, @PathVariable long userId) {
        filmService.addLike(id, userId);
        return ResponseEntity.ok("Like поставлен");
    }

    @DeleteMapping("/{id}/like/{userId}")
    public ResponseEntity<String> removeLike(@PathVariable long id, @PathVariable long userId) {
        filmService.removeLike(id, userId);
        return ResponseEntity.ok("Like удален");
    }

    @GetMapping("/popular")
    public ResponseEntity<List<Film>> getBestFilms(@RequestParam(required = false, defaultValue = "10") int count) {
        List<Film> popularFilms = filmService.getBestFilms(count).getBody();
        return ResponseEntity.ok(popularFilms);
    }
}