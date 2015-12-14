package ru.mail.track.kolodzey.Server.store;

/**
 * Created by DKolodzey on 14.12.15.
 */
public interface UserStore {
    User getUserByLogin(String login);
    User getUserById(Integer id);
    User createUser(String login, String password);
}
