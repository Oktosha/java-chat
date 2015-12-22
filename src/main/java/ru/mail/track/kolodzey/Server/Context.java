package ru.mail.track.kolodzey.Server;

import ru.mail.track.kolodzey.Server.store.MessageStore;
import ru.mail.track.kolodzey.Server.store.User;
import ru.mail.track.kolodzey.Server.store.UserStore;

/**
 * Created by DKolodzey on 14.12.15.
 */
public class Context {
    public User user; //TODO: make tread local
    public UserStore userStore;
    public MessageStore messageStore;

    Context(UserStore userStore, MessageStore messageStore) {
        this.user = null;
        this.messageStore = messageStore;
        this.userStore = userStore;
    }
}
