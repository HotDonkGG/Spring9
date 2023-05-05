package filmorateapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import filmorateapp.model.User;
import org.springframework.web.bind.annotation.*;
import filmorateapp.service.ValidationService;

import java.util.ArrayList;
import java.util.List;
// Надо ловить конкретную ошибку
@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private List<User> users = new ArrayList<>();
    private int nextId = 0;
    @Autowired
    private ValidationService validationService; // загуглить инжекты бина виды

    @PostMapping
    public User addUsers(@RequestBody User user) {
        try {
            validationService.validate(user);
            user.setId(nextId++);
            users.add(user);
            log.info("Добавлен новый пользователь" + user.getId());
        } catch (Exception e) {
            log.error("Ошибка добавления пользователя" + e.getMessage());
        }
        return user;
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable int id, @RequestBody User user) {
        try {
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getId() == id) {
                    validationService.validate(user);
                    users.set(i, user);
                    log.info("Обновление пользователя с айди" + id);
                    return user;
                }
            }
        } catch (Exception e) {
            log.error("Ошибка обновления пользователя " + e.getMessage());
        }
        return user;
    }

    @GetMapping
    public List<User> getAllUsers() {
        try {
            if (!users.isEmpty()) {
                log.info("Получите список пользователей " + users);
                return users;
            }
        } catch (Exception e) {
            log.error("Мапа пуста " + e.getMessage());
        }
        return users;
    }
}