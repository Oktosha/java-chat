package ru.mail.track.kolodzey.Server.store;

/**
 * Created by DKolodzey on 14.12.15.
 */
public class User {
    public User() {
        id = null;
        login = null;
        password = null;
    }
    public User(Integer id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public final Integer id;
    public final String login;
    public final String password;
}
