package filmorateapp.service;

import filmorateapp.model.validation.ValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import filmorateapp.model.Film;
import filmorateapp.model.User;
import filmorateapp.model.validation.ValidationException;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ValidationServiceTest {
    private ValidationService validationService;

    private LocalDate getDate(int daysToAdd) {
        LocalDate currentDate = LocalDate.now();
        return currentDate.plusDays(daysToAdd);
    }

    private LocalDate getDate(int month, int day) {
        Calendar calendar = Calendar.getInstance();
        return LocalDate.of(1800, month, day);
    }

    @BeforeEach
    public void init() {
        validationService = new ValidationService();
    }

    @Test
    void validateUserEmptyEmail() {
        User user = new User();
        user.setEmail("");
        user.setLogin("TestUser1");
        user.setBirthday(getDate(-1));
        assertThrows(ValidationException.class, () -> validationService.validate(user));
    }

    @Test
    void validateUserExceptDogEmail() {
        User user = new User();
        user.setEmail("TestUser2");
        user.setLogin("TestUser2");
        user.setBirthday(getDate(-1));
        assertThrows(ValidationException.class, () -> validationService.validate(user));
    }

    @Test
    void validateUserLoginSpace() {
        User user = new User();
        user.setEmail("TestUser3@gmail.com");
        user.setLogin("Test User3");
        user.setBirthday(getDate(-1));
        assertThrows(ValidationException.class, () -> validationService.validate(user));
    }

    @Test
    void validateUserBornFuture() {
        User user = new User();
        user.setEmail("TestUser4@gmail.com");
        user.setLogin("TestUser4");
        user.setBirthday(getDate(1));
        assertThrows(ValidationException.class, () -> validationService.validate(user));
    }

    @Test
    void validateFilmEmptyName() {
        Film film = new Film();
        film.setName("");
        film.setDescription("Test1FilmDescription");
        film.setReleaseDate(getDate(-1));
        film.setDuration(25L);
        assertThrows(ValidationException.class, () -> validationService.validate(film));
    }

    @Test
    void validateFilmDescription() {
        Film film = new Film();
        film.setName("StarWars");
        film.setDescription("StarWars".repeat(1000));
        film.setReleaseDate(getDate(-1));
        film.setDuration(180L);
        assertThrows(ValidationException.class, () -> validationService.validate(film));
    }

    @Test
    void validateFilmRelease() {
        Film film = new Film();
        film.setName("LordOfTheRings");
        film.setDescription("TwoCastle");
        film.setReleaseDate(getDate(10, 10));
        film.setDuration(240L);
        assertThrows(ValidationException.class, () -> validationService.validate(film));
    }

    @Test
    void validateFilmDuration() {
        Film film = new Film();
        film.setName("HarryPotter");
        film.setDescription("HarryPotterPart1");
        film.setReleaseDate(getDate(-1));
        film.setDuration(-1L);
        assertThrows(ValidationException.class, () -> validationService.validate(film));
    }
}