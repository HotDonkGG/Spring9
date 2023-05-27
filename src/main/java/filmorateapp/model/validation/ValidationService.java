package filmorateapp.model.validation;

import org.springframework.stereotype.Service;
import filmorateapp.model.Film;
import filmorateapp.model.User;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;

@Service
public class ValidationService {

    public void validate(User user) throws ValidationException {
        if (user.getEmail() == null || user.getEmail().isEmpty() || !user.getEmail().contains("@")) {
            throw new ValidationException("Емейл юзера не может быть пустым и должен содержать @");
        } else if (user.getLogin() == null || user.getLogin().contains(" ")) {
            throw new ValidationException("Логин пользователя не может быть пустым или содержать пробелы");
        } else if (user.getBirthday() != null && user.getBirthday().isAfter(ChronoLocalDate.from(LocalDateTime.
                now()))) {
            throw new ValidationException("Пользователь не может быть рождён в будущем");
        }
    }

    public void validate(Film film) throws ValidationException {
        if (film.getName() == null || film.getName().isEmpty()) {
            throw new ValidationException("фильм не может быть пустым");
        } else if (film.getDescription() != null && film.getDescription().length() > 200) {
            throw new ValidationException("Описание фильма более 200 символов");
        } else if (film.getReleaseDate() != null && film.getReleaseDate().isBefore(ChronoLocalDate.from(LocalDateTime.
                of(1895, 12, 28, 0, 0)))) {
            throw new ValidationException("Дата релиза не может быть раньше чем 28/12/1895г.");
        } else if (film.getDuration() != 0 && film.getDuration() >= 0) {
            throw new ValidationException("Фильм должен иметь положительное значение");
        }
    }
}