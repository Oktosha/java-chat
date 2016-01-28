package ru.mail.track.kolodzey.Server.store;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by DKolodzey on 14.12.15.
 */
public class Chat {
    public Chat() {
        this.id = null;
        this.messages = new ArrayList<>();
        this.participants = new HashSet<>();
    }
    public Chat(Integer id, Set<Integer> participants) {
        this.id = id;
        this.participants = participants;
        this.messages = new ArrayList<>();
    }

    public Integer id;
    public List<Integer> messages;
    public Set<Integer> participants;
}
