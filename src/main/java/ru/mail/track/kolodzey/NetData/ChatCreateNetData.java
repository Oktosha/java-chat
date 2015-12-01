package ru.mail.track.kolodzey.NetData;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DKolodzey on 27.11.15.
 */
public class ChatCreateNetData extends NetData {
    public ChatCreateNetData() {
        this(null, null);
    }

    public ChatCreateNetData(List<Integer> participantIds, Sender sender) {
        this.requestedAction = Action.CHAT_CREATE;
        this.sender = sender;
        this.participantIds = participantIds;
    }
    @JsonDeserialize(as=ArrayList.class, contentAs=Integer.class)
    public List<Integer> participantIds;

    @Override
    public String toString() {
        if (participantIds == null)
            return super.toString() + " null";
        return super.toString() + " " + participantIds.toString();
    }
}
