package filmorateapp.storage.user;

import filmorateapp.model.User;

import java.util.List;

public interface UserStorage {
    User addUser(User user);

    User getUserById(long id);

    User updateUser(long id, User user);

    boolean deleteUser(long id);

    User addFriend(long userId, long friendId);

    User removeFriend(long userId, long friendId);

    List<User> getMutualFriends(long id, long otherId);

    List<User> getFriendsByUserId(long id);

    List<User> findAllUsers();
}