package ru.mail.track.kolodzey;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DKolodzey on 27.11.15.
 */
public class ChatCreateNetData extends NetData {
    ChatCreateNetData() {
        this(null, null);
    }

    ChatCreateNetData(List<Integer> participantIds, Sender sender) {
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
