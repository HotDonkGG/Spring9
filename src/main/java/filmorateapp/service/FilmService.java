package filmorateapp.service;

import filmorateapp.model.Film;
import filmorateapp.storage.FilmStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;


@Service
public class FilmService {
    private final FilmStorage filmStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public void addLike(Film film) {
        film.setLikes(film.getLikes() + 1);
    }


    public void removeLike(Film film) {
        int likes = film.getLikes();
        if (likes > 0) {
            film.setLikes(--likes);
        }
    }

    public List<Film> getBestFilms(int count) {
        List<Film> films = filmStorage.getBestFilms(count);
        return films != null ? films : Collections.emptyList();
    }
}
