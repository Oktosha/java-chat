package ru.mail.track.kolodzey.NetData;

import java.time.Instant;

/**
 * Created by DKolodzey on 01.12.15.
 */
public class ChatSendNetData extends NetData {
    public Integer chatID;
    public String message;
    public Instant timestamp;
    public ChatSendNetData() {
        this(null, null, null, null);
    }
    public ChatSendNetData(Integer chatID, String message, Instant timestamp, Sender sender) {
        this.requestedAction = Action.CHAT_SEND;
        this.sender = sender;
        this.message = message;
        this.chatID = chatID;
        this.timestamp = timestamp;
    }
    @Override
    public String toString() {
        return super.toString() + " " + chatID + " " + message;
    }
}
