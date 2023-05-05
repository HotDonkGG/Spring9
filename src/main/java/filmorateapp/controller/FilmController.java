package filmorateapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import filmorateapp.model.Film;
import org.springframework.web.bind.annotation.*;
import filmorateapp.service.ValidationService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private List<Film> films = new ArrayList<>();
    private int nextId = 0;
    @Autowired
    private ValidationService validationService;

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
            if (!films.isEmpty()) {// валидация в отдельном методе
                log.info("Получите Список фильмов" + films);
                return films;
            }
        } catch (Exception e) {
            log.error("Мапа пуста " + e.getMessage());
        }
        return films;
    }
}