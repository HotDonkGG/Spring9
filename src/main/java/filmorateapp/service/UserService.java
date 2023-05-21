package filmorateapp.service;

import filmorateapp.model.User;
import filmorateapp.storage.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void addFriend(User user, User friend) {
        Set<Long> friends = user.getFriends();
        friends.add(friend.getId());
        user.setFriends(friends);
    }

    public void removeFriend(User user, User friend) {
        Set<Long> friends = user.getFriends();
        friends.remove(friend.getId());
        user.setFriends(friends);
    }

    public List<User> getMutualFriends(User user1, User user2) {
        Set<Long> friends1 = user1.getFriends();
        Set<Long> friends2 = user2.getFriends();
        Set<Long> mutualFriends = new HashSet<>(friends1);
        mutualFriends.retainAll(friends2);
        return null;
    }
}