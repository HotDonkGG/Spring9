package filmorateapp.storage.user;

import filmorateapp.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {
    private final HashSet<User> users = new HashSet<>();
    private long nextId = 0;

    /**
     * Добавление пользователя, возвращает user;
     */
    @Override
    public User addUser(User user) {
        user.setId(nextId++);
        users.add(user);
        return user;
    }

    /**
     * Получение пользователя по Id если есть, если пользователя нет возвращает null;
     */
    @Override
    public User getUserById(long id) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    /**
     * Обновление пользователя по Id;
     */
    @Override
    public User updateUser(long id, User user) {
        for (User existingUser : users) {
            if (existingUser.getId() == id) {
                users.remove(existingUser);
                users.add(user);
                return user;
            }
        }
        return null;
    }

    /**
     * Удаление пользователя
     */
    @Override
    public boolean deleteUser(long id) {
        Iterator<User> iterator = users.iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user.getId() == id) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    @Override
    public User addFriend(long userId, long friendId) {
        getUserById(userId).getFriends().add(Long.valueOf(friendId));
        getUserById(friendId).getFriends().add(Long.valueOf(userId));
        return getUserById(userId);
    }

    @Override
    public User removeFriend(long userId, long friendId) {
        getUserById(userId).getFriends().remove(friendId);
        getUserById(friendId).getFriends().remove(userId);
        return getUserById(userId);
    }

    @Override
    public List<User> getFriendsByUserId(long id) {
        return findAllUsers().stream()
                .filter(user -> user.getFriends().contains(id))
                .collect(Collectors.toList());
    }

    @Override
    public List<User> findAllUsers() {
        return new ArrayList<>(users);
    }

    @Override
    public List<User> getMutualFriends(long userId, long friendId) {
        List<User> mutualFriends = new ArrayList<>();
        for (Long id : getUserById(userId).getFriends()) {
            if (getUserById(friendId).getFriends().contains(id)) {
                mutualFriends.add(getUserById(id));
            }
        }
        return mutualFriends;
    }
}