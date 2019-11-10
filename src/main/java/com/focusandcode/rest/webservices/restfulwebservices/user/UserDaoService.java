package com.focusandcode.rest.webservices.restfulwebservices.user;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class UserDaoService {
    private static List<User> users = new ArrayList<>();
    private static int userCount = 5;

    {
        users.add(new User(1, "Kalenda", new Date()));
        users.add(new User(2, "Rachel",  new Date()));
        users.add(new User(3, "Sara",  new Date()));
        users.add(new User(4, "Meta",  new Date()));
        users.add(new User(5, "Samuel",  new Date()));
    }

    public static User save(User user) {
        try{
            User oldUser = findOne(user.getId());
            throw new UserAlreadyExistException(String.format("User with id-%s already exist", oldUser.getId()));
        } catch (UserNotFoundException ue) {
            user.setId(++userCount);
            users.add(user);
        }
        return user;
    }

    public static List<User> findAll() {
        return users;
    }

    public static User findOne(Integer id) {
        for(User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        throw new UserNotFoundException("id-" + id);
    }

    public static User delete(Integer id) {
        User deletedUser = findOne(id);
        boolean remove = users.remove(deletedUser);
        return deletedUser;
    }
}
