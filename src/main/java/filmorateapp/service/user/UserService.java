package filmorateapp.service.user;

import filmorateapp.model.User;
import filmorateapp.model.validation.ValidationService;
import filmorateapp.storage.user.UserStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequestMapping("/users")
@Slf4j
public class UserService {
    private final List<User> users = new ArrayList<>();
    private long nextId = 0;
    private UserStorage userStorage;
    private ValidationService validationService;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User addFriend(User user, long friendId) {
        User friend = userStorage.getUserById(friendId);
        if (friend != null) {
            user.getFriends().add(friendId);
            friend.getFriends().add(user.getId());
            userStorage.updateUser(user.getId(), user);
            userStorage.updateUser(friendId, friend);
            return user;
        }
        return null;
    }

    public User removeFriend(User user, long friendId) {
        User friend = userStorage.getUserById(friendId);
        if (friend != null) {
            user.getFriends().remove(friendId);
            friend.getFriends().remove(user.getId());
            userStorage.updateUser(user.getId(), user);
            userStorage.updateUser(friendId, friend);
            return user;
        }
        return null;
    }

    public List<User> getMutualFriends(long userId, long friendId) {
        User user = userStorage.getUserById(userId);
        User friend = userStorage.getUserById(friendId);
        if (user != null && friend != null) {
            return userStorage.getMutualFriends(userId, friendId);
        }
        return Collections.emptyList();
    }

    public List<User> getUserFriends(long userId) {
        return userStorage.getFriendsByUserId(userId);
    }

    public User addUsers(User user) {
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

    public User updateUser(long id, User user) {
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