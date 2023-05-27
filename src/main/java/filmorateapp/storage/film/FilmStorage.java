package filmorateapp.storage.film;

import filmorateapp.model.Film;

import java.util.List;

public interface FilmStorage {
    Film addFilm(Film film);

    Film updateFilm(long id, Film film);

    Film getFilmById(long id);

    boolean deleteFilm(long id);

    List<Film> getBestFilms(int count);
}