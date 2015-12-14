package ru.mail.track.kolodzey.Server.store;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by DKolodzey on 14.12.15.
 */
public class DummyUserStoreImpl implements UserStore {
    private List<User> users = new ArrayList<>();
    private Map<String, Integer> idByLogin = new HashMap<>();

    @Override
    public User getUserByLogin(String login) {
        if (!idByLogin.containsKey(login))
            return null;
        Integer id = idByLogin.get(login);
        return getUserById(id);
    }

    @Override
    public User getUserById(Integer id) {
        if (id >= users.size())
            return null;
        return users.get(id);
    }

    @Override
    public User createUser(String login, String password) {
        if (idByLogin.containsKey(login))
            return null;
        User user = new User(users.size(), login, password);
        users.add(user);
        idByLogin.put(user.login, user.id);
        return user;
    }
}
