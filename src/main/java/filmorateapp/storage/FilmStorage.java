package filmorateapp.storage;

import filmorateapp.model.Film;

import java.util.List;

public interface FilmStorage {
    Film add(Film film);

    Film update(long id, Film film);

    Film getById(long id);

    boolean delete(long id);

    List<Film> getBestFilms(int count);
}