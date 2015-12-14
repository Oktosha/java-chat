package ru.mail.track.kolodzey.Server;

/**
 * Created by DKolodzey on 14.12.15.
 */
public interface UserStore {
    User getUserByLogin(String login);
    User getUserById(Long id);
    User createUser(String login, String password);
}
