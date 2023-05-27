package filmorateapp.storage.film;

import filmorateapp.model.Film;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final HashSet<Film> films = new HashSet<>();
    private int nextId = 0;

    /**
     * Добавление фильма
     */
    @Override
    public Film addFilm(Film film) {
        film.setId(nextId++);
        films.add(film);
        return film;
    }

    /**
     * Обновление фильма
     */
    @Override
    public Film updateFilm(long id, Film film) {
        for (Film existingFilm : films) {
            if (existingFilm.getId() == id) {
                films.remove(existingFilm);
                films.add(film);
                return film;
            }
        }
        return null;
    }

    /**
     * Получение фильма по Id;
     */
    @Override
    public Film getFilmById(long id) {
        for (Film film : films) {
            if (film.getId() == id) {
                return film;
            }
        }
        return null;
    }

    /**
     * Удаление фильма;
     */
    @Override
    public boolean deleteFilm(long id) {
        Iterator<Film> iterator = films.iterator();
        while (iterator.hasNext()) {
            Film film = iterator.next();
            if (film.getId() == id) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    /**
     * Получение 10 лучших фильмов;
     */
    @Override
    public List<Film> getBestFilms(int count) {
        List<Film> allFilms = getBestFilms(count);
        allFilms.sort(Comparator.comparingInt(Film::getLike).reversed());
        List<Film> bestFilms = allFilms.stream()
                .limit(count)
                .collect(Collectors.toList());
        return bestFilms;
    }
}