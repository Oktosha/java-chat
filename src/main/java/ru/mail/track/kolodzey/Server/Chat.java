package ru.mail.track.kolodzey.Server;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by DKolodzey on 14.12.15.
 */
public class Chat {
    public Chat(Integer id, Set<Integer> participants) {
        this.id = id;
        this.participants = participants;
        this.messages = new ArrayList<>();
    }

    public Integer id;
    public List<Integer> messages;
    public Set<Integer> participants;
}
